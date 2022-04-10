package com.example.nnpia_sem_backend.dto;

import com.example.nnpia_sem_backend.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationPagingDto {
    private List<Reservation> reservationList;
    private Long numberOfElements;
}
