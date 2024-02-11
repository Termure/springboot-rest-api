package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.springboot.payload.PostDto;
import net.javaguides.springboot.payload.PostResponse;
import net.javaguides.springboot.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static net.javaguides.springboot.utils.AppConstants.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public PostResponse getAllPosts(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER,      required = false) int pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE,        required = false) int pageSize,
            @RequestParam(defaultValue = DEFAULT_SORTING_CRITERIA, required = false) String sortCriteria,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION  , required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortCriteria, sortDir);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable long id){
        return ResponseEntity.ok(postService.updatePostById(postDto, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post Deleted successfully!");
    }

}
