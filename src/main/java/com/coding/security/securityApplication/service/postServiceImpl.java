package com.coding.security.securityApplication.service;


import com.coding.security.securityApplication.dto.postDTO;
import com.coding.security.securityApplication.entity.User;
import com.coding.security.securityApplication.entity.postEntity;
import com.coding.security.securityApplication.exceptions.ResourceNotFound;
import com.coding.security.securityApplication.repositroy.postRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class postServiceImpl implements  postService{

    private final postRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<postDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity ->modelMapper.map(postEntity, postDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public postDTO createNewPost(postDTO inputPost) {
        User user= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postEntity postEntity=modelMapper.map(inputPost, postEntity.class);//converting to postEntity
        postEntity.setAuthor(user);
        return modelMapper.map(postRepository.save(postEntity), postDTO.class);
    }

    @Override
    public postDTO getById(Long id) {
        postEntity postEntity=postRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFound("Post with Id not fount"));

        return modelMapper.map(postEntity, postDTO.class);
    }

    @Override
    public postDTO updatePost(postDTO inputPost, Long id) {
        postEntity olderPost=postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Post with Id not found"));
        inputPost.setId(id);
        modelMapper.map(inputPost,olderPost);
        postEntity savedEntity=postRepository.save(olderPost);
        return modelMapper.map(savedEntity, postDTO.class);
    }
}
