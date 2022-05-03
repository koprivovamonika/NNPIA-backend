package com.example.nnpia_sem_backend.integrationTest;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.entity.ProcedureStatus;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import com.example.nnpia_sem_backend.repository.ProcedureRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import com.example.nnpia_sem_backend.service.BeautySalonService;
import com.example.nnpia_sem_backend.service.ProcedureService;
import com.example.nnpia_sem_backend.service.ReservationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Date;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationServiceTest {

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    BeautySalonService beautySalonService;

    @AfterAll
    public void cleanUp() {
        BeautyProcedure testProcedure = procedureRepository.findByName("Test procedure");
        Reservation reservationByBeautyProcedure = reservationRepository.findReservationByBeautyProcedure(testProcedure);
        reservationRepository.deleteById(reservationByBeautyProcedure.getId());
        procedureRepository.deleteById(testProcedure.getId());
    }

    @Test
    void createReservationAfterCreateProcedure() throws Exception {
        BeautyProcedure procedure = new BeautyProcedure();
        procedure.setName("Test procedure");
        procedure.setDescription("This is test procedure.");
        procedure.setPrice(400.0);
        procedure.setStatus(ProcedureStatus.ACTIVE);

        BeautyProcedure procedureCreate = procedureService.createProcedure(procedure);
        BeautyProcedure newProcedure = procedureService.findProcedureByName("Test procedure");

        Reservation reservation = new Reservation();
        reservation.setReservationDate(new Date());
        reservation.setStartTime(LocalTime.of(8, 0, 0));
        reservation.setEndTime(LocalTime.of(9, 0, 0));
        reservation.setEmail("xyz@email.cz");
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setBeautyProcedure(newProcedure);
        reservation.setBeautySalon(beautySalonService.findById(1L));

        Reservation reservationCreate = reservationService.createReservation(reservation);

        Assertions.assertEquals(procedureCreate, procedure);
        Assertions.assertEquals(reservationCreate, reservation);
    }
}