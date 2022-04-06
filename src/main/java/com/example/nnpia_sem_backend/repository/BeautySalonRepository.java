package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.BeautySalon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface BeautySalonRepository extends JpaRepository<BeautySalon, Long> {
    BeautySalon findBeautySalonById(Long id);
}
