package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.repository.ProcedureRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class ProcedureServiceTest {

    ProcedureService procedureService = new ProcedureService(Mockito.mock(ProcedureRepository.class), Mockito.mock(ReservationRepository.class));

    @Test
    void updateProcedureNotExist() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            Mockito.when(procedureService.procedureRepository.findById(1L))
                    .thenReturn(Optional.empty());
            BeautyProcedure beautyProcedure = new BeautyProcedure();
            Assertions.assertTrue(true);
            procedureService.updateProcedure(beautyProcedure, 1L);
        });
        Assertions.assertEquals("Procedure with ID: 1 was not found!", exception.getMessage());
    }

    @Test
    void deleteProcedureNotExist() {
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            Mockito.when(procedureService.procedureRepository.findById(1L))
                    .thenReturn(Optional.empty());
            BeautyProcedure beautyProcedure = new BeautyProcedure();
            procedureService.deleteProcedure(1L);
        });
        Assertions.assertEquals("Procedure with ID: 1 was not found!", exception.getMessage());
    }
}