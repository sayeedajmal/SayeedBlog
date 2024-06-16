package com.strong.PostService.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.strong.PostService.Service.LikeService;
import com.strong.PostService.model.Likes;

import jakarta.validation.Valid;

@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("saveLike")
    public void SaveLike(@RequestBody @Valid Likes like) {
        like.setLikeId(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8));
        likeService.saveLike(like);
    }

}
