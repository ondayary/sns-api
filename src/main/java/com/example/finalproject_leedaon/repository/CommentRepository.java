package com.example.finalproject_leedaon.repository;

import com.example.finalproject_leedaon.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}