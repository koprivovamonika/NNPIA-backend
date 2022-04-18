package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.dto.CreateReservationDtoIn;
import com.example.nnpia_sem_backend.dto.ReservationPagingDto;
import com.example.nnpia_sem_backend.dto.TimeSlotDto;
import com.example.nnpia_sem_backend.entity.ApiResponse;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import com.example.nnpia_sem_backend.service.BeautySalonService;
import com.example.nnpia_sem_backend.service.ProcedureService;
import com.example.nnpia_sem_backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping()
public class ReservationController {
    @Autowired
    BeautySalonService beautySalonService;

    @Autowired
    ProcedureService procedureService;

    @Autowired
    ReservationService reservationService;


    @GetMapping("/public/reservation")
    public List<TimeSlotDto> getAll(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, Long id) {
        return beautySalonService.getTimeSlotsForDate(date, id);
    }

    @PostMapping("/public/reservation")
    public ApiResponse<Boolean> createReservation(@RequestBody @Valid CreateReservationDtoIn createReservationDtoIn) throws ParseException {
        Reservation reservation = convertToEntity(createReservationDtoIn);
        if(reservationService.createReservation(reservation)){
            return new ApiResponse<>(HttpStatus.OK.value(), "Reservation created successfully.", true);
        }
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Create reservation failed.", false);
    }

    @PutMapping("/api/reservation/confirm")
    public ApiResponse<Boolean> confirmReservation(Long resId){
        if (reservationService.confirmReservation(resId)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "Reservation confirmed successfully.", true);
        }
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Confirm reservation failed.", false);
    }

    @PutMapping("/api/reservation/asDone")
    public ApiResponse<Boolean> setAsDone(Long resId){
        if (reservationService.setAsDone(resId)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "Reservation was set as done.", true);
        }
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Setting reservation as done failed.", false);
    }

    @DeleteMapping("/api/reservation")
    public ApiResponse<Boolean> cancelReservation(Long id, String description) {
        if (reservationService.cancelReservation(id, description)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "Reservation cancelled successfully.", true);
        }
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Cancel reservation failed.", false);
    }

    @GetMapping("/api/reservation")
    public ApiResponse<ReservationPagingDto> getAllByDateAndStatus(Long salonId,@DateTimeFormat(pattern = "yyyy-MM-dd") Date date, ReservationStatus status, Pageable pageable){
        Page<Reservation> pagedResult;
        if(status != ReservationStatus.CREATED && status != ReservationStatus.CONFIRMED && status != ReservationStatus.DONE){
            pagedResult = reservationService.getReservationByDate(pageable, salonId, date);
        }else{
            pagedResult = reservationService.getReservationByDateAndStatus(pageable, salonId, date, status);
        }
        ReservationPagingDto reservationPagingDto = convertToPagingDto(pagedResult);
        return new ApiResponse<>(HttpStatus.OK.value(), "", reservationPagingDto);
    }


    private Reservation convertToEntity(CreateReservationDtoIn createReservationDtoIn) throws ParseException {
        Reservation reservation = new Reservation();
        reservation.setEmail(createReservationDtoIn.getEmail());
        reservation.setReservationDate(createReservationDtoIn.getDate());
        reservation.setStartTime(createReservationDtoIn.getTimeSlotDto().getStartTime());
        reservation.setEndTime(createReservationDtoIn.getTimeSlotDto().getEndTime());
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setBeautyProcedure(procedureService.findById(procedureService.findIdByName(createReservationDtoIn.getProcedureDto().getName())));
        reservation.setBeautySalon(beautySalonService.findById(createReservationDtoIn.getSalonId()));

        return reservation;
    }

    private ReservationPagingDto convertToPagingDto(Page<Reservation> pagedResult){
        if (pagedResult.hasContent()) {
            ReservationPagingDto reservationPagingDto = new ReservationPagingDto();
            reservationPagingDto.setReservationList(pagedResult.getContent());
            reservationPagingDto.setNumberOfElements(pagedResult.getTotalElements());
            return reservationPagingDto;
        } else {
            return new ReservationPagingDto();
        }
    }
}
