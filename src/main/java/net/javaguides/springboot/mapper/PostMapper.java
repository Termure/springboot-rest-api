package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.payload.PostDto;

import java.util.stream.Collectors;

public class PostMapper {

    public static PostDto mapToPostDto(Post post){
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getContent(),
                post.getComments().stream().map(CommentMapper::maptoCommentDto).collect(Collectors.toList())
        );
    }

    public static Post mapToPost(PostDto postDto){
        return new Post(
                postDto.getId(),
                postDto.getTitle(),
                postDto.getDescription(),
                postDto.getContent()
        );
    }
}
