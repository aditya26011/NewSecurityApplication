package com.coding.security.securityApplication.utils;

import com.coding.security.securityApplication.dto.postDTO;
import com.coding.security.securityApplication.entity.User;
import com.coding.security.securityApplication.service.postService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

//Responsible for securing all my posts
@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final postService postService;

    boolean isOwnerOfPost(Long postId){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    postDTO post= postService.getById(postId);
    return post.getAuthor().getId().equals(user.getId());
    }
}
