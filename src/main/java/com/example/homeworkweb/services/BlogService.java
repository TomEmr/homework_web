package com.example.homeworkweb.services;

import com.example.homeworkweb.models.Blog;
import com.example.homeworkweb.models.dtos.CreateNewBlogDTO;
import com.example.homeworkweb.repositories.BlogRepository;
import com.example.homeworkweb.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;

    public Blog createNewBlog(CreateNewBlogDTO createNewBlogDTO) {

        Blog blog = Blog.builder()
                .title(createNewBlogDTO.getTitle())
                .content(createNewBlogDTO.getContent())
                .tags(tagRepository.findAll())
                .build();

        blogRepository.save(blog);
        return blog;

    }

}
