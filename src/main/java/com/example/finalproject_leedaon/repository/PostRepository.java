package com.example.finalproject_leedaon.repository;

import com.example.finalproject_leedaon.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
