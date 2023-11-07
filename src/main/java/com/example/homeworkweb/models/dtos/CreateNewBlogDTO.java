package com.example.homeworkweb.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateNewBlogDTO {

    private String title;
    private String content;
    private List<Long> tagIDs;
}
