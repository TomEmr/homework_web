package com.example.homeworkweb.models.dtos;
import com.example.homeworkweb.models.Blog;
import com.example.homeworkweb.models.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDTO {

    private Long id;
    private String title;
    private String content;
    private List<Long> tags;

    public BlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.tags = blog.getTags().stream().map(Tag::getId).collect(Collectors.toList());
    }
}
