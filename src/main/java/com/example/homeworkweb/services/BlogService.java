package com.example.homeworkweb.services;

import com.example.homeworkweb.exceptions.BlogNotFoundException;
import com.example.homeworkweb.models.Blog;
import com.example.homeworkweb.models.Tag;
import com.example.homeworkweb.models.dtos.BlogDTO;
import com.example.homeworkweb.models.dtos.CreateNewBlogDTO;
import com.example.homeworkweb.models.dtos.UpdateBlogDTO;
import com.example.homeworkweb.repositories.BlogRepository;
import com.example.homeworkweb.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;

    public BlogDTO createNewBlog(CreateNewBlogDTO createNewBlogDTO) {
        List<Tag> tags = tagRepository
                .findAllById(createNewBlogDTO.getTagIDs());
        Blog blog = Blog.builder()
                .title(createNewBlogDTO.getTitle())
                .content(createNewBlogDTO.getContent())
                .tags(tags)
                .build();
        blogRepository.save(blog);
        return new BlogDTO(blog);
    }

    public Page<BlogDTO> getAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable)
                .map(BlogDTO::new);
    }

    public BlogDTO getBlogById(Long id) {
        Blog blog = blogRepository
                .findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        return new BlogDTO(blog);
    }

    public BlogDTO updateBlog(UpdateBlogDTO updateBlogDTO) {
        Blog blog = blogRepository
                .findById(updateBlogDTO.getId())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        List<Tag> tags = tagRepository.findAllById(updateBlogDTO.getTagIDs());
        blog.setTitle(updateBlogDTO.getTitle());
        blog.setContent(updateBlogDTO.getContent());
        blog.setTags(tags);
        blogRepository.save(blog);
        return new BlogDTO(blog);
    }

    public BlogDTO deleteBlogById(Long id) {
        Blog blog = blogRepository
                .findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        blogRepository.delete(blog);
        return new BlogDTO(blog);
    }

}
