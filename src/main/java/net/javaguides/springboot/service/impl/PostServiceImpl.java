package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.mapper.PostMapper;
import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.payload.PostResponse;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortCriteria, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortCriteria).ascending()
                : Sort.by(sortCriteria).descending();

        // create Pageable object
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content of page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(PostMapper::mapToPostDto).collect(Collectors.toList());

        return PostResponse.builder()
            .content(content)
            .pageNo(posts.getNumber())
            .pageSize(posts.getSize())
            .totalElements(posts.getTotalElements())
            .totalPages(posts.getTotalPages())
            .last(posts.isLast())
        .build();
    }

    @Override
    public PostDto getPostById(long id) {
        return postRepository.findById(id)
                .map(PostMapper::mapToPostDto)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        return postRepository.findById(id)
                .map(post -> {
                    post = Post.builder()
                        .id(id) //very important -> when builder creates a new object, if the id is not set, new object will be created because id is null
                        .title(postDto.getTitle())
                        .description(postDto.getDescription())
                        .content(postDto.getContent())
                        .comments(post.getComments())
                        .build();
                    return PostMapper.mapToPostDto(postRepository.save(post));
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }
}
