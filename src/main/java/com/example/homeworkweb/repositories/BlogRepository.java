package com.example.homeworkweb.repositories;

import com.example.homeworkweb.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
