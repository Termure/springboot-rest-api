package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.entity.Category;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.payload.PostResponse;
import net.javaguides.springboot.repository.CategoryRepository;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.service.PostService;
import org.modelmapper.ModelMapper;
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

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto){
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));

        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDto.class);
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

        List<PostDto> content = listOfPosts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

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
                .map(post -> modelMapper.map(post, PostDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", postDto.getCategoryId()));
        return postRepository.findById(id)
                .map(post -> {
                    post = Post.builder()
                        .id(id) //very important -> when builder creates a new object, if the id is not set, new object will be created because id is null
                        .title(postDto.getTitle())
                        .description(postDto.getDescription())
                        .content(postDto.getContent())
                        .comments(post.getComments())
                        .category(category)
                        .build();

                    return modelMapper.map(postRepository.save(post), PostDto.class);
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public void deletePostById(long id) { postRepository.deleteById(id); }

    @Override
    public List<PostDto> getPostByCategoryId(Long id){
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", id));
        List<Post> posts = postRepository.findByCategoryId(id);
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
