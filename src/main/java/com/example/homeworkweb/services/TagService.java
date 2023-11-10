package com.example.homeworkweb.services;

import com.example.homeworkweb.models.Tag;
import com.example.homeworkweb.models.dtos.CreateNewTagDTO;
import com.example.homeworkweb.models.dtos.TagDTO;
import com.example.homeworkweb.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    @Transactional
    public List<TagDTO> findAll() {
        return tagRepository.findAll().stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public TagDTO save(CreateNewTagDTO createNewTagDTO) {
        Tag tag = Tag.builder()
                .name(createNewTagDTO.getName())
                .build();
        tagRepository.save(tag);
        return new TagDTO(tag.getId(), tag.getName());
    }

    @Transactional
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

}
