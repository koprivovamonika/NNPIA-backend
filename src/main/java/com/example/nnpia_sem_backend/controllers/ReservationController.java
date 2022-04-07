package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.dto.CreateReservationDtoIn;
import com.example.nnpia_sem_backend.dto.ProcedureDto;
import com.example.nnpia_sem_backend.dto.TimeSlotDto;
import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.service.BeautySalonService;
import com.example.nnpia_sem_backend.service.ProcedureService;
import com.example.nnpia_sem_backend.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/public/reservation")
public class ReservationController {
    @Autowired
    BeautySalonService beautySalonService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public List<TimeSlotDto> getAll(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Long id) {
        return beautySalonService.getTimeSlotsForDate(date, id);
    }

    @PostMapping
    public void createReservation(@RequestBody @Valid CreateReservationDtoIn createReservationDtoIn, Long procedureId, Long salonId) throws ParseException {
        Reservation reservation = convertToEntity(createReservationDtoIn);
        reservation.setBeautyProcedure(procedureService.findById(procedureId));
        reservation.setBeautySalon(beautySalonService.findById(salonId));
        reservationService.createReservation(reservation);
    }

    private Reservation convertToEntity(CreateReservationDtoIn createReservationDtoIn) throws ParseException {
        Reservation reservation = modelMapper.map(createReservationDtoIn, Reservation.class);
        reservation.setEmail(createReservationDtoIn.getEmail());
        reservation.setReservationDate(createReservationDtoIn.getDate());
        reservation.setStartTime(createReservationDtoIn.getTimeSlotDto().getStartTime());
        reservation.setEndTime(createReservationDtoIn.getTimeSlotDto().getEndTime());

        return reservation;
    }
}
