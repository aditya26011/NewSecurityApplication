package com.coding.security.securityApplication.service;


import com.coding.security.securityApplication.dto.postDTO;

import java.util.List;

public interface postService {
    List<postDTO> getAllPosts();

    postDTO createNewPost(postDTO inputPost);

    postDTO getById(Long id);

    postDTO updatePost(postDTO inputPost, Long id);


}
