package com.strong.PostService.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strong.PostService.Repository.CommentRepo;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Comments;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostService postService;

    @Transactional
    public Comments SaveComment(Comments comment) throws BlogException {
        comment.set_id(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        comment.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        comment.setUpdatedAt(null);
        postService.addComment(comment);
        return commentRepo.save(comment);
    }

    public List<Comments> getAllByPostId(String postId) throws BlogException {
        List<Comments> byPostId = commentRepo.findByPostId(postId);
        if (!byPostId.isEmpty()) {
            return byPostId;
        } else {
            throw new BlogException("Can't Found Comment by PostId : " + postId);
        }
    }

    public long countCmt(String postId) {
        return commentRepo.countBypostId(postId);
    }

    public Comments findCmtById(String cmtId) throws BlogException {
        return commentRepo.findById(cmtId)
                .orElseThrow(() -> new BlogException("Cant' Found Comment by postId: " + cmtId));
    }

    public Comments findByUserId(String userId) throws BlogException {
        return commentRepo.findById(userId)
                .orElseThrow(() -> new BlogException("Cant' Found Comment by postId: " + userId));
    }

    public void removeCmtById(String cmtId) throws BlogException {
        Comments byId = commentRepo.findById(cmtId)
                .orElseThrow(() -> new BlogException("Can't Found Comment_Id : " + cmtId));
        commentRepo.delete(byId);
    }

    @Transactional
    public void updateCmt(Comments cmt) throws BlogException {
        Comments existingComment = commentRepo.findById(cmt.get_id())
                .orElseThrow(() -> new BlogException("Can't find comment by ID: " + cmt.get_id()));
        existingComment.setContent(cmt.getContent());
        existingComment.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        commentRepo.save(existingComment);
    }
}
