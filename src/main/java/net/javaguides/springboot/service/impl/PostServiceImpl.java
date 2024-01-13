package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.mapper.PostMapper;
import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.service.PostService;
import org.springframework.stereotype.Service;
import net.javaguides.springboot.exception.*;

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
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(post -> PostMapper.mapToPostDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        return postRepository.findById(id)
                .map(post -> {
                    return PostMapper.mapToPostDto(post);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        return postRepository.findById(id)
                .map(_post -> {
                    _post.setTitle(postDto.getTitle());
                    _post.setDescription(postDto.getDescription());
                    _post.setContent(postDto.getContent());
                    return PostMapper.mapToPostDto(postRepository.save(_post));
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

}
