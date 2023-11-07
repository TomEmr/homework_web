package com.example.homeworkweb.repositories;

import com.example.homeworkweb.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
