package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Login findByUserName(String username);
}
