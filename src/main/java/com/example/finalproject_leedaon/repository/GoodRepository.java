package com.example.finalproject_leedaon.repository;

import com.example.finalproject_leedaon.domain.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Integer> {
}
