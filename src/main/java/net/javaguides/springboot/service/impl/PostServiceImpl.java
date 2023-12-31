package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.mapper.PostMapper;
import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto){
        Post savedPost = postRepository.save(PostMapper.mapToPost(postDto));

        return PostMapper.mapToPostDto(savedPost);
    }

    @Override
    public List<PostDto> getAllPosts(){
        List<Post> posts = postRepository.findAll();

        return posts.stream().map(post -> PostMapper.mapToPostDto(post)).collect(Collectors.toList());
    }
}
