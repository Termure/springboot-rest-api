package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.mapper.PostMapper;
import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto){
        Post savedPost = postRepository.save(PostMapper.mapToPost(postDto));

        return PostMapper.mapToPostDto(savedPost);
    }
}
