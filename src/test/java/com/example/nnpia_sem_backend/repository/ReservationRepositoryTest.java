package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    BeautySalonRepository beautySalonRepository;

    @Autowired
    ProcedureRepository procedureRepository;


    @Test
    void findReservationByReservationDate() {
        List<Reservation> reservationByReservationDateBefore = reservationRepository.findReservationByReservationDate(new Date());

        BeautyProcedure procedure = new BeautyProcedure();
        procedure.setName("My new procedure");
        procedure.setDescription("This is test procedure.");
        procedure.setPrice(400.0);
        procedure.setStatus(ProcedureStatus.ACTIVE);
        procedureRepository.save(procedure);

        Reservation reservation = new Reservation();
        reservation.setReservationDate(new Date());
        reservation.setStartTime(LocalTime.of(8,00,00));
        reservation.setEndTime(LocalTime.of(8,00,00));
        reservation.setEmail("xyz@email.cz");
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setBeautySalon(beautySalonRepository.findBeautySalonById(1L));
        reservation.setBeautyProcedure(procedure);
        reservationRepository.save(reservation);

        List<Reservation> reservationByReservationDateAfter = reservationRepository.findReservationByReservationDate(new Date());

        Assertions.assertEquals(reservationByReservationDateBefore.size()+1, reservationByReservationDateAfter.size());
    }

    @Test
    void existsByBeautyProcedure_Id() {
        BeautyProcedure procedure = new BeautyProcedure();
        procedure.setName("My new procedure");
        procedure.setDescription("This is test procedure.");
        procedure.setPrice(400.0);
        procedure.setStatus(ProcedureStatus.ACTIVE);
        procedureRepository.save(procedure);

        Reservation reservation = new Reservation();
        reservation.setReservationDate(new Date());
        reservation.setStartTime(LocalTime.of(8,00,00));
        reservation.setEndTime(LocalTime.of(8,00,00));
        reservation.setEmail("xyz@email.cz");
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setBeautySalon(beautySalonRepository.findBeautySalonById(1L));
        reservation.setBeautyProcedure(procedure);
        reservationRepository.save(reservation);

        BeautyProcedure newProcedure = procedureRepository.findByName("My new procedure");
        Boolean exists = reservationRepository.existsByBeautyProcedure_Id(newProcedure.getId());
        Assertions.assertTrue(exists);
    }
}