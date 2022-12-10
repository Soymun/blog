package com.example.posts.Controllers;

import com.example.posts.DTO.CommentDto;
import com.example.posts.DTO.GetCommentByOrder;
import com.example.posts.DTO.GetCommentDto;
import com.example.posts.Response.ResponseDto;
import com.example.posts.Service.Impl.CommentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog")
@CrossOrigin
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/comment")
    public ResponseEntity<?> getComments(@RequestBody GetCommentDto getCommentDto){
        return ResponseEntity.ok(ResponseDto.builder().body(commentService.getComment(getCommentDto)).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/comment/order")
    public ResponseEntity<?> getCommentsByOrder(@RequestBody GetCommentByOrder getCommentDto){
        return ResponseEntity.ok(ResponseDto.builder().body(commentService.getCommentByPredicate(getCommentDto.getPostId(), getCommentDto.getMap())).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(ResponseDto.builder().body(commentService.saveComment(commentDto)).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(ResponseDto.builder().body("Suggest").build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(ResponseDto.builder().body(commentService.updateComment(commentDto)).build());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/comment")
    public ResponseEntity<?> patchComment(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(ResponseDto.builder().body(commentService.patchComment(commentDto)).build());
    }
}
