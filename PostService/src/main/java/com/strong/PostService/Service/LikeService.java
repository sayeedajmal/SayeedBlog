package com.strong.PostService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strong.PostService.Repository.LikeRepo;
import com.strong.PostService.Repository.PostRepo;
import com.strong.PostService.Utils.BlogException;
import com.strong.PostService.model.Likes;
import com.strong.PostService.model.Posts;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private PostRepo postRepo;

    public void saveLike(Likes like) throws BlogException {
        Optional<Posts> postbyId = postRepo.findById(like.getPostId());
        if (postbyId.isPresent()) {
            likeRepo.save(like);
        } else
            throw new BlogException("can't Found post by PostId: " + like.getPostId());
    }

    public List<Likes> getAll() {
        return likeRepo.findAll();
    }

    public void removeLike(String userId) throws BlogException {
        likeRepo.delete(findByUserId(userId));
    }

    public Likes findByUserId(String userId) throws BlogException {
        return likeRepo.findByUserId(userId).orElseThrow(() -> new BlogException("Can't Found Like by UserId : " + userId));
    }
}
