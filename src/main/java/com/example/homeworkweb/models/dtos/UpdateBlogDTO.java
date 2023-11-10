package com.example.homeworkweb.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBlogDTO {

    @NotNull(message = "ID is required")
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not be longer than 255 characters")
    private String title;
    @NotBlank(message = "Content is required")
    private String content;
    private List<Long> tagIDs;
}
