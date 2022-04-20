package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.dto.TimeSlotDto;
import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.entity.BeautySalon;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import com.example.nnpia_sem_backend.repository.BeautySalonRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class BeautySalonServiceTest {

    BeautySalonService beautySalonService = new BeautySalonService(Mockito.mock(BeautySalonRepository.class), Mockito.mock(ReservationRepository.class));
    Long beautySalonId;

    @BeforeEach
    void setUp() {
        beautySalonId = 1L;
        BeautySalon beautySalon = new BeautySalon();
        beautySalon.setOpeningTime(LocalTime.of(8, 00, 00));
        beautySalon.setClosingTime(LocalTime.of(16, 00, 00));

        Mockito.when(beautySalonService.beautySalonRepository.findBeautySalonById(beautySalonId))
                .thenReturn(beautySalon);
    }

    @Test
    void getTimeSlotsForDateWeekend() {
        try {
            String date = "23/04/2022";
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            List<TimeSlotDto> timeSlotsForDate = beautySalonService.getTimeSlotsForDate(date1, beautySalonId);
            Assertions.assertTrue(timeSlotsForDate.isEmpty());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTimeSlotsForDate() {
        try {
            String date = "25/04/2022";
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);

            Reservation reservation = new Reservation();
            reservation.setStartTime(LocalTime.of(8, 00, 00));
            reservation.setEndTime(LocalTime.of(9, 00, 00));

            Reservation reservation2 = new Reservation();
            reservation2.setStartTime(LocalTime.of(10, 00, 00));
            reservation2.setEndTime(LocalTime.of(11, 00, 00));

            List<Reservation> reservations = new ArrayList<>();
            reservations.add(reservation);
            reservations.add(reservation2);

            Mockito.when(beautySalonService.reservationRepository.findReservationByReservationDateAndStatusIsNot(date1, ReservationStatus.DELETED))
                    .thenReturn(reservations);

            Integer expectedNotFreeTimeSlots = 2;
            Integer actualNotFreeTimeSlots = 0;

            List<TimeSlotDto> timeSlotsForDate = beautySalonService.getTimeSlotsForDate(date1, beautySalonId);
            for (TimeSlotDto timeSlotDto : timeSlotsForDate) {
                if(!timeSlotDto.isSlotFree()){
                    actualNotFreeTimeSlots++;
                }
            }

            Assertions.assertEquals(expectedNotFreeTimeSlots, actualNotFreeTimeSlots);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}