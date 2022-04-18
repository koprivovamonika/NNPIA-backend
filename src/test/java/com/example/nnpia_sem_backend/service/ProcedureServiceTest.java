package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.repository.ProcedureRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(MockitoJUnitRunner.class)
class ProcedureServiceTest {

    ProcedureService procedureService = new ProcedureService();

    @BeforeEach
    void setUp() {
        procedureService.reservationRepository = Mockito.mock(ReservationRepository.class);
        procedureService.procedureRepository = Mockito.mock(ProcedureRepository.class);
    }

    @Test
    void updateProcedureSameName() {
        Mockito.when(procedureService.procedureRepository.findByNameAndIdIsNot("testName",1L))
                .thenReturn(Optional.of(new BeautyProcedure()));
        BeautyProcedure beautyProcedure = new BeautyProcedure();
        beautyProcedure.setName("testName");
        assertFalse(procedureService.updateProcedure(beautyProcedure, 1L));
    }

    @Test
    void updateProcedureNotExist() {
        Mockito.when(procedureService.procedureRepository.findById(1L))
                .thenReturn(Optional.empty());
        BeautyProcedure beautyProcedure = new BeautyProcedure();
        assertFalse(procedureService.updateProcedure(beautyProcedure, 1L));
    }

    @Test
    void deleteProcedureNotExist() {
        Mockito.when(procedureService.procedureRepository.findById(1L))
                .thenReturn(Optional.empty());
        BeautyProcedure beautyProcedure = new BeautyProcedure();
        assertFalse(procedureService.deleteProcedure(1L));
    }
}