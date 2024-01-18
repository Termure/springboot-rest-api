package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.entity.Comment;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.CommentMapper;
import net.javaguides.springboot.payload.CommentDto;
import net.javaguides.springboot.repository.CommentRepository;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.service.CommentService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = CommentMapper.MapToComment(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        return CommentMapper.maptoCommentDto(commentRepository.save(comment));
    }
}
