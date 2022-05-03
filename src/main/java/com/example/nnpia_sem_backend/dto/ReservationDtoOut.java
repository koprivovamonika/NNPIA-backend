package com.example.nnpia_sem_backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReservationDtoOut {
    private String email;
    private TimeSlotDto timeSlotDto;
    private Date date;
    private Long salonId;
}
