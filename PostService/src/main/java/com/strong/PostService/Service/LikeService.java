package com.strong.PostService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strong.PostService.Repository.LikeRepo;
import com.strong.PostService.model.Likes;

@Service
public class LikeService {

    @Autowired
    private LikeRepo likeRepo;

    public void saveLike(Likes like) {
        likeRepo.save(like);
    }
}
