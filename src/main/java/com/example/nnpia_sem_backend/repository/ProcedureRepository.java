package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcedureRepository extends JpaRepository<BeautyProcedure, Long> {
    BeautyProcedure findByName(String name);

    Optional<BeautyProcedure> findByNameAndIdIsNot(String name, Long id);
}
