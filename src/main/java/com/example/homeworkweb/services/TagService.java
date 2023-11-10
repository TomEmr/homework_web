package com.example.homeworkweb.services;

import com.example.homeworkweb.models.Tag;
import com.example.homeworkweb.models.dtos.CreateNewTagDTO;
import com.example.homeworkweb.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Transactional
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Transactional
    public List<Tag> getTagsByIDs(List<Long> tagIDs) {
        return tagRepository.findAllById(tagIDs);
    }

    @Transactional
    public Tag save(CreateNewTagDTO createNewTagDTO) {
        Tag tag = Tag.builder()
                .name(createNewTagDTO.getName())
                .build();

        tagRepository.save(tag);
        return tag;
    }

}
