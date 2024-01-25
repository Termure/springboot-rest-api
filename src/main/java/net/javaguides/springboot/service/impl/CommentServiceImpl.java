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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CommentDto> getCommentsByPostId(long postId){
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(CommentMapper::maptoCommentDto).collect(Collectors.toList());
    }

    public CommentDto getCommentById(long commentId, long postId){
        return commentRepository.findByIdAndPostId(commentId, postId)
                .map(CommentMapper::maptoCommentDto)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
    }

    public CommentDto updateCommentById(long commentId, long postId, CommentDto commentDto){
        return commentRepository.findByIdAndPostId(commentId, postId)
                .map(comment -> {
                    comment = Comment.builder()
                        .id(commentId)
                        .body(commentDto.getBody())
                        .email((commentDto.getEmail()))
                        .name(commentDto.getName())
                        .post(comment.getPost())
                        .build();
                    return CommentMapper.maptoCommentDto(commentRepository.save(comment));

                }).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
    }

    public void deleteCommentByIdAndPost(long commentId, long postId){
        Optional<Comment> comment = commentRepository.findByIdAndPostId(commentId, postId);
        comment.ifPresent(object -> commentRepository.deleteById(object.getId()));
    }
}
