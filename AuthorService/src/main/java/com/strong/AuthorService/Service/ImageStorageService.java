package com.strong.AuthorService.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.MongoGridFSException;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.strong.AuthorService.Utils.AuthorException;

@Service
public class ImageStorageService {
    @Autowired
    private GridFSBucket gridFSBucket;

    public String uploadImage(MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getName();
            InputStream inputStream = file.getInputStream();
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024)
                    .metadata(new org.bson.Document("type", "image"));
            ObjectId fileId = gridFSBucket.uploadFromStream(fileName, inputStream, options);
            return fileId.toHexString();
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

    public InputStreamResource getImageStream(String fileId) throws AuthorException {
        try {
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(new ObjectId(fileId));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read;
            while ((read = downloadStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return new InputStreamResource(inputStream);
        } catch (Exception e) {
            throw new AuthorException(e.getLocalizedMessage());
        }
    }

    public void deleteImage(String fileId) throws AuthorException {
        try {
            gridFSBucket.delete(new ObjectId(fileId));
        } catch (IllegalArgumentException | MongoGridFSException e) {
            throw new AuthorException(e.getLocalizedMessage());
        }
    }

}