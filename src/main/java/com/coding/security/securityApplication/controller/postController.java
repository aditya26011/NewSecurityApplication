package com.coding.security.securityApplication.controller;



import com.coding.security.securityApplication.dto.postDTO;
import com.coding.security.securityApplication.service.postService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class postController {
    private final postService postService;

    @GetMapping
    public List<postDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public postDTO getById(@PathVariable("postId") Long id){
        return postService.getById(id);
    }

    @PostMapping
    public postDTO createPost(@RequestBody postDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @PutMapping("/{postId}")
    public postDTO updatePost(@RequestBody postDTO inputPost, @PathVariable("postId") Long id){
        return postService.updatePost(inputPost,id);
    }
}
