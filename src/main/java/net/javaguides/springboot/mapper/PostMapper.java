package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.payload.PostDto;
public class PostMapper {

    public static PostDto mapToPostDto(Post post){
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getContent()
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
