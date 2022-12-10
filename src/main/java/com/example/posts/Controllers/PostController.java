package com.example.posts.Controllers;

import com.example.posts.DTO.PostDto;
import com.example.posts.DTO.PostPredicateDto;
import com.example.posts.Response.ResponseDto;
import com.example.posts.Service.Impl.PostServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
@CrossOrigin
public class PostController {

    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postController) {
        this.postService = postController;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDto.builder().body(postService.getPostById(id)).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/post")
    public ResponseEntity<?> savePost(@RequestBody PostDto postDto){
        return ResponseEntity.ok(ResponseDto.builder().body(postService.savePost(postDto)).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/post")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto){
        return ResponseEntity.ok(ResponseDto.builder().body(postService.updatePost(postDto)).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/post/all")
    public ResponseEntity<?> getPosts(){
        return ResponseEntity.ok(ResponseDto.builder().body(postService.getAllPost()).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/post/order")
    public ResponseEntity<?> getPostByOrder(@RequestBody PostPredicateDto postPredicateDto){
        return ResponseEntity.ok(ResponseDto.builder().body(postService.getPostByPredicate(postPredicateDto.getMap())).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/post")
    public ResponseEntity<?> patchPost(@RequestBody PostDto postDto){
        return ResponseEntity.ok(ResponseDto.builder().body(postService.patchPost(postDto)).build());
    }
}
