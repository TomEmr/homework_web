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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;

    public BlogDTO createNewBlog(CreateNewBlogDTO createNewBlogDTO) {
        List<Tag> tags = new ArrayList<>();
        if (!createNewBlogDTO.getTagIDs().isEmpty()) {
            tags = tagRepository.findAllById(createNewBlogDTO.getTagIDs());
        }
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

    public Page<BlogDTO> searchBlogs(String keyword, Pageable pageable) {
        return blogRepository.findByTitleContainingOrContentContaining(keyword, pageable)
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
        Set<Tag> currentTags = new HashSet<>(blog.getTags());
        Set<Tag> updatedTags = new HashSet<>(tagRepository.findAllById(updateBlogDTO.getTagIDs()));
        currentTags.clear();
        currentTags.addAll(updatedTags);
        blog.setTitle(updateBlogDTO.getTitle());
        blog.setContent(updateBlogDTO.getContent());
        blog.setTags(new ArrayList<>(currentTags));
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
