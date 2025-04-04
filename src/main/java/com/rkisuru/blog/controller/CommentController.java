package com.rkisuru.blog.controller;

import com.rkisuru.blog.dto.CommentRequest;
import com.rkisuru.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/posts")
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @Valid @RequestBody CommentRequest commentRequest) throws Exception {
        return ResponseEntity.ok(commentService.createComment(postId, commentRequest));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable Long commentId, @RequestBody CommentRequest request, @AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok(commentService.editComment(commentId, request, user));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long comment_id, @AuthenticationPrincipal OAuth2User user) {

        commentService.deleteComment(comment_id, user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Comment deleted successfully");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("comments/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}
