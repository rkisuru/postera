package com.rkisuru.blog.dto;

import com.rkisuru.blog.entity.Comment;
import com.rkisuru.blog.type.PostType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private long id;
    private String title;
    private PostType type;
    private String content;
    private byte[] coverImage;
    private String createdBy;
    private LocalDateTime createdDate;
    private int likeCount;
    private int viewCount;
    private Set<String> tags;
    private List<Comment> comments;
}
