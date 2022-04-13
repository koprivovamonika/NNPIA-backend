package com.example.nnpia_sem_backend.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Date;

@Data
public class CreateReservationDtoIn {
    @Email(message = "Email should be valid")
    private String email;
    private TimeSlotDto timeSlotDto;
    private Date date;
    private ProcedureDto procedureDto;
    private Long salonId;
}
