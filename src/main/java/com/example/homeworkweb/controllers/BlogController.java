package com.example.homeworkweb.controllers;

import com.example.homeworkweb.models.dtos.CreateNewBlogDTO;
import com.example.homeworkweb.models.dtos.UpdateBlogDTO;
import com.example.homeworkweb.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<?> getAllBlogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
                blogService.getAllBlogs(PageRequest.of(page - 1, size)));
    }

    @PostMapping
    public ResponseEntity<?> createNewBlog(@RequestBody CreateNewBlogDTO createNewBlogDTO) {
        return ResponseEntity.ok(blogService.createNewBlog(createNewBlogDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @PutMapping
    public ResponseEntity<?> updateBlog(@RequestBody UpdateBlogDTO updateBlogDTO) {
        return ResponseEntity.ok(blogService.updateBlog(updateBlogDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.deleteBlogById(id));
    }
}
