package com.example.finalproject_leedaon.repository;

import com.example.finalproject_leedaon.domain.entity.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    Page<Alarm> findByUserId(Integer id, Pageable pageable);
}
