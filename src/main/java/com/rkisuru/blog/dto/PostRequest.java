package com.rkisuru.blog.dto;

import com.rkisuru.blog.type.PostType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public record PostRequest(

        @NotNull(message = "Title is required")
        @NotEmpty(message = "Title is required")
        String title,

        @NotNull(message = "Content is required")
        @NotEmpty(message = "Content is required")
        String content,

        PostType type,
        Set<String> tags
) {
}
