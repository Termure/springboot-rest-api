package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.Comment;
import net.javaguides.springboot.payload.CommentDto;
public class CommentMapper {

    public static CommentDto maptoCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getName(),
                comment.getEmail(),
                comment.getBody()
        );
    }

    public static Comment MapToComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getName(),
                commentDto.getEmail(),
                commentDto.getBody()
        );
    }
}
