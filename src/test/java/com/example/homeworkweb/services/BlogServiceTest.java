package com.example.homeworkweb.services;

import com.example.homeworkweb.exceptions.BlogNotFoundException;
import com.example.homeworkweb.models.Blog;
import com.example.homeworkweb.models.Tag;
import com.example.homeworkweb.models.dtos.BlogDTO;
import com.example.homeworkweb.models.dtos.CreateNewBlogDTO;
import com.example.homeworkweb.models.dtos.UpdateBlogDTO;
import com.example.homeworkweb.repositories.BlogRepository;
import com.example.homeworkweb.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Test createNewBlog with valid data")
    void createNewBlog_Success() {
        List<Long> tagIds = Arrays.asList(1L, 2L);
        CreateNewBlogDTO dto = new CreateNewBlogDTO("Title", "Content", tagIds);
        List<Tag> tags = Arrays.asList(new Tag(1L, "Tag1", null), new Tag(2L, "Tag2", null));
        when(tagRepository.findAllById(tagIds)).thenReturn(tags);
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> invocation.getArgument(0));
        BlogDTO result = blogService.createNewBlog(dto);
        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        assertEquals("Content", result.getContent());
        assertEquals(2, result.getTags().size());
        verify(tagRepository).findAllById(tagIds);
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    @DisplayName("Test createNewBlog with no tags")
    void createNewBlog_NoTags() {
        CreateNewBlogDTO dto = new CreateNewBlogDTO("Title", "Content", Collections.emptyList());
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> invocation.getArgument(0));
        BlogDTO result = blogService.createNewBlog(dto);
        assertNotNull(result);
        assertTrue(result.getTags().isEmpty());
        verify(tagRepository, never()).findAllById(anyList());
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    @DisplayName("Test getAllBlogs returns a non-empty page")
    void getAllBlogs_WithContent() {
        List<Tag> mockTags = Collections.singletonList(new Tag(1L, "Tag1", null));
        Blog blog1 = Blog.builder().id(1L).title("Blog1").content("Content1").tags(mockTags).build();
        Blog blog2 = Blog.builder().id(2L).title("Blog2").content("Content2").tags(mockTags).build();
        List<Blog> blogs = Arrays.asList(blog1, blog2);
        Page<Blog> page = new PageImpl<>(blogs);
        when(blogRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<BlogDTO> result = blogService.getAllBlogs(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(blogRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test getAllBlogs returns an empty page")
    void getAllBlogs_Empty() {
        Page<Blog> page = new PageImpl<>(Collections.emptyList());
        when(blogRepository.findAll(any(Pageable.class))).thenReturn(page);
        Page<BlogDTO> result = blogService.getAllBlogs(Pageable.unpaged());
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(blogRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test searchBlogs returns a non-empty page when keyword matches")
    void searchBlogs_WithMatchingKeyword() {
        List<Tag> mockTags = Collections.singletonList(new Tag(1L, "Tag1", null));
        Blog blog1 = Blog.builder().id(1L).title("Blog1").content("Content1").tags(mockTags).build();
        Blog blog2 = Blog.builder().id(2L).title("Blog2").content("Content2").tags(mockTags).build();
        List<Blog> blogs = Arrays.asList(blog1, blog2);
        Page<Blog> page = new PageImpl<>(blogs);
        when(blogRepository.findByTitleContainingOrContentContaining(anyString(), any(Pageable.class))).thenReturn(page);
        Page<BlogDTO> result = blogService.searchBlogs("keyword", Pageable.unpaged());
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(blogRepository).findByTitleContainingOrContentContaining(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("Test searchBlogs returns an empty page when no matches are found")
    void searchBlogs_NoMatchingKeyword() {
        Page<Blog> emptyPage = new PageImpl<>(Collections.emptyList());
        when(blogRepository.findByTitleContainingOrContentContaining(anyString(), any(Pageable.class))).thenReturn(emptyPage);
        Page<BlogDTO> result = blogService.searchBlogs("nonexistent", Pageable.unpaged());
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        verify(blogRepository).findByTitleContainingOrContentContaining(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("Test getBlogById successfully retrieves a blog")
    void getBlogById_Success() {
        List<Tag> mockTags = Collections.singletonList(new Tag(1L, "Tag1", null));
        Blog blog = Blog.builder().id(1L).title("Test Blog").content("Test Content").tags(mockTags).build();
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blog));
        BlogDTO result = blogService.getBlogById(1L);
        assertNotNull(result);
        assertEquals("Test Blog", result.getTitle());
        assertEquals("Test Content", result.getContent());
        assertNotNull(result.getTags());
        assertEquals(1, result.getTags().size());
        verify(blogRepository).findById(anyLong());
    }


    @Test
    @DisplayName("Test getBlogById throws BlogNotFoundException for non-existent blog")
    void getBlogById_NotFound() {
        when(blogRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BlogNotFoundException.class, () -> blogService.getBlogById(99L));
        verify(blogRepository).findById(anyLong());
    }


    @Test
    @DisplayName("Test updateBlog successfully updates a blog")
    void updateBlog_Success() {
        UpdateBlogDTO dto = new UpdateBlogDTO(1L, "Updated Title", "Updated Content", Collections.singletonList(1L));
        List<Tag> updatedTags = Collections.singletonList(new Tag(1L, "Tag1", null));
        List<Tag> mockTags = Collections.singletonList(new Tag(1L, "Tag1", null));
        Blog blog = Blog.builder().id(1L).title("Test Blog").content("Test Content").tags(mockTags).build();
        when(blogRepository.findById(dto.getId())).thenReturn(Optional.of(blog));
        when(tagRepository.findAllById(dto.getTagIDs())).thenReturn(updatedTags);
        when(blogRepository.save(any(Blog.class))).thenAnswer(invocation -> invocation.getArgument(0));
        BlogDTO result = blogService.updateBlog(dto);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Content", result.getContent());
        assertEquals(1, result.getTags().size());
        verify(blogRepository).findById(dto.getId());
        verify(tagRepository).findAllById(dto.getTagIDs());
        verify(blogRepository).save(any(Blog.class));
    }

    @Test
    @DisplayName("Test updateBlog throws BlogNotFoundException for non-existent blog")
    void updateBlog_NotFound() {
        UpdateBlogDTO dto = new UpdateBlogDTO(99L, "Title", "Content", Collections.emptyList());
        when(blogRepository.findById(dto.getId())).thenReturn(Optional.empty());
        assertThrows(BlogNotFoundException.class, () -> blogService.updateBlog(dto));
        verify(blogRepository).findById(dto.getId());
        verify(tagRepository, never()).findAllById(anyList());
    }

    @Test
    @DisplayName("Test deleteBlogById successfully deletes a blog")
    void deleteBlogById_Success() {
        List<Tag> mockTags = Collections.singletonList(new Tag(1L, "Tag1", null));
        Blog blog = Blog.builder().id(1L).title("Test Blog").content("Test Content").tags(mockTags).build();
        when(blogRepository.findById(blog.getId())).thenReturn(Optional.of(blog));
        BlogDTO result = blogService.deleteBlogById(blog.getId());
        assertNotNull(result);
        verify(blogRepository).findById(blog.getId());
        verify(blogRepository).delete(blog);
    }

    @Test
    @DisplayName("Test deleteBlogById throws BlogNotFoundException for non-existent blog")
    void deleteBlogById_NotFound() {
        Long blogId = 99L;
        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());
        assertThrows(BlogNotFoundException.class, () -> blogService.deleteBlogById(blogId));
        verify(blogRepository).findById(blogId);
        verify(blogRepository, never()).delete(any(Blog.class));
    }

}