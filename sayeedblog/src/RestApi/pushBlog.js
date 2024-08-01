const extractBlobUrls = (content) => {
  const blobUrlPattern = /blob:[^\s"]+/g;
  return [...new Set(content.match(blobUrlPattern) || [])];
};

const uploadImage = async (blobUrl) => {
  try {
    const response = await fetch(blobUrl);
    const blob = await response.blob();

    const formData = new FormData();
    formData.append("files", blob);

    const uploadResponse = await fetch(
      "http://localhost:8080/api/images/upload",
      {
        method: "POST",
        body: formData,
      }
    );

    if (!uploadResponse.ok) {
      throw new Error(`Failed to upload image: ${blobUrl}`);
    }

    const [fileId] = await uploadResponse.json();
    return fileId; 
  } catch (error) {
    console.error(`Error uploading image ${blobUrl}:`, error);
    throw error;
  }
};

const replaceBlobUrlsInContent = async (content) => {
  const blobUrls = extractBlobUrls(content);
  const replacements = {};
  const uploadedImageIds = [];

  for (const blobUrl of blobUrls) {
    try {
      const fileId = await uploadImage(blobUrl);
      uploadedImageIds.push(fileId);
      replacements[blobUrl] = "/api/images/" + fileId;
    } catch {
      continue;
    }
  }

  let updatedContent = content;
  for (const [blobUrl, newUrl] of Object.entries(replacements)) {
    updatedContent = updatedContent.replace(blobUrl, newUrl);
  }

  return { updatedContent, uploadedImageIds };
};

export const pushBlog = async (blogData) => {
  console.log("PushBlog:", JSON.stringify(blogData));

  try {
    const { updatedContent, uploadedImageIds } = await replaceBlobUrlsInContent(
      blogData.content
    );

    const updatedBlogData = {
      ...blogData,
      content: updatedContent,
      images: uploadedImageIds,
    };

    const response = await fetch("http://localhost:8080/post", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedBlogData),
    });

    if (!response.ok) {
      throw new Error("Failed to post blog");
    }

    return await response.json();
  } catch (error) {
    console.error("Error posting blog:", error);
    throw error;
  }
};
