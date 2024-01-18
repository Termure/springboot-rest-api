package net.javaguides.springboot.service;

import net.javaguides.springboot.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
