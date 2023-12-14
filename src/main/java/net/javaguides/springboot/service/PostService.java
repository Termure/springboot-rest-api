package net.javaguides.springboot.service;

import net.javaguides.springboot.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
