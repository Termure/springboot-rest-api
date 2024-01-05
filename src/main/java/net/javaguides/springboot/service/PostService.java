package net.javaguides.springboot.service;

import net.javaguides.springboot.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
}
