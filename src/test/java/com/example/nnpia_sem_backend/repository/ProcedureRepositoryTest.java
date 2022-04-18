package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.entity.ProcedureStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProcedureRepositoryTest {

    @Autowired
    ProcedureRepository procedureRepository;

    BeautyProcedure procedure;
    List<BeautyProcedure> allByStatusBefore;

    @BeforeEach
    void setUp() {
        allByStatusBefore = procedureRepository.findAllByStatus(ProcedureStatus.ACTIVE);
        procedure = new BeautyProcedure();
        procedure.setName("My new procedure");
        procedure.setDescription("This is test procedure.");
        procedure.setPrice(400.0);
        procedure.setStatus(ProcedureStatus.ACTIVE);
        procedureRepository.save(procedure);
    }

    @Test
    void findByName() {
        BeautyProcedure beautyProcedure = procedureRepository.findByName("My new procedure");
        Assertions.assertEquals(beautyProcedure, procedure);
    }

    @Test
    void findByNameAndIdIsNot() {
        Optional<BeautyProcedure> beautyProcedure = procedureRepository.findByNameAndIdIsNot("My new procedure", 0L);
        Assertions.assertFalse(beautyProcedure.isEmpty());
    }

    @Test
    void findAllByStatus() {
        List<BeautyProcedure> allByStatusAfter = procedureRepository.findAllByStatus(ProcedureStatus.ACTIVE);
        Assertions.assertEquals(allByStatusBefore.size()+1, allByStatusAfter.size());
    }
}