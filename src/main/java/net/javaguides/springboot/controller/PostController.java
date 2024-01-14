package net.javaguides.springboot.controller;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.payload.PostResponse;
import net.javaguides.springboot.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public PostResponse getAllPosts(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ){
        return postService.getAllPosts(pageNo, pageSize);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable long id){
        return ResponseEntity.ok(postService.updatePostById(postDto, id));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post Deleted successfully");
    }

}
