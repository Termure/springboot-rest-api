package net.javaguides.springboot.service;

import net.javaguides.springboot.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateCommentById(long commentId, long postId, CommentDto commentDto);
}
