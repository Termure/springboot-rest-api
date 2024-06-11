package net.javaguides.springboot.service;

import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortCriteria, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePostById(PostDto postDto, long id);
    void deletePostById(long id);
    List<PostDto> getPostByCategoryId(Long categoryId);
}
