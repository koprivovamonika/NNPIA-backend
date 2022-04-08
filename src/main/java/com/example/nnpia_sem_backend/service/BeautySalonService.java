package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.dto.TimeSlotDto;
import com.example.nnpia_sem_backend.entity.BeautySalon;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.repository.BeautySalonRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BeautySalonService {
    @Autowired
    BeautySalonRepository beautySalonRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public BeautySalon findById(Long id){
        return beautySalonRepository.findBeautySalonById(id);
    }

    public List<TimeSlotDto> getTimeSlotsForDate(Date date, Long beautySalonId) {
        List<TimeSlotDto> timeSlots = new ArrayList<>();
        BeautySalon beautySalon = findById(beautySalonId);
        LocalDateTime localDateTime = dateToLocaleDateTime(date);
        if(localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY){
            return new ArrayList<>();
        }
        List<Reservation> reservations = reservationRepository.findReservationByReservationDate(date);
        LocalTime openingTime = beautySalon.getOpeningTime();
        LocalTime closingTime =beautySalon.getClosingTime();

        while (openingTime.getHour() < closingTime.getHour()){
            boolean freeSlot = true;
            for (Reservation reservation : reservations) {
                if ((!openingTime.isBefore(reservation.getStartTime()) || !openingTime.plusHours(1).isBefore(reservation.getStartTime().plusMinutes(1))) &&
                        (!openingTime.isAfter(reservation.getEndTime().minusMinutes(1)) || !openingTime.plusHours(1).isAfter(reservation.getEndTime()))) {
                           freeSlot = false;
                           break;
                        }
            }
            timeSlots.add(new TimeSlotDto(openingTime, openingTime.plusHours(1), freeSlot));
            openingTime = openingTime.plusHours(1);
        }

        return timeSlots;
    }

    private LocalDateTime dateToLocaleDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
