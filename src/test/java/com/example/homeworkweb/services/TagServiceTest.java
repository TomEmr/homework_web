package com.example.homeworkweb.services;

import com.example.homeworkweb.models.Tag;
import com.example.homeworkweb.models.dtos.CreateNewTagDTO;
import com.example.homeworkweb.models.dtos.TagDTO;
import com.example.homeworkweb.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Find all tags successfully")
    void findAll() {
        List<Tag> mockTags = Arrays.asList(
                new Tag(1L, "Tag1", null),
                new Tag(2L, "Tag2", null)
        );
        when(tagRepository.findAll()).thenReturn(mockTags);
        List<TagDTO> result = tagService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Tag1", result.get(0).getName());
        assertEquals("Tag2", result.get(1).getName());
        verify(tagRepository).findAll();
    }

    @Test
    @DisplayName("Test findAll returns an empty list when no tags are available")
    void findAll_WithEmptyList() {
        when(tagRepository.findAll()).thenReturn(Collections.emptyList());
        List<TagDTO> result = tagService.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(tagRepository).findAll();
    }

    @Test
    @DisplayName("Save a new tag successfully")
    void save() {
        CreateNewTagDTO dto = new CreateNewTagDTO("NewTag");
        Tag mockTag = new Tag(1L, "NewTag", null);
        when(tagRepository.save(any(Tag.class))).thenReturn(mockTag);
        TagDTO result = tagService.save(dto);
        assertNotNull(result);
        assertEquals("NewTag", result.getName());
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    @DisplayName("Test save throws an exception when repository fails")
    void save_ThrowsException() {
        CreateNewTagDTO dto = new CreateNewTagDTO("NewTag");
        when(tagRepository.save(any(Tag.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> tagService.save(dto));
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    @DisplayName("Delete a tag by id successfully")
    void deleteById() {
        tagService.deleteById(1L);
        verify(tagRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Test deleteById handles non-existing tag gracefully")
    void deleteById_NonExistingTag() {
        doThrow(new EmptyResultDataAccessException(1)).when(tagRepository).deleteById(99L);
        assertThrows(EmptyResultDataAccessException.class, () -> tagService.deleteById(99L));
        verify(tagRepository).deleteById(99L);
    }
}