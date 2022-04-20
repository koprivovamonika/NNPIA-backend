package com.example.nnpia_sem_backend.dto;

import lombok.Data;

@Data
public class CancelDtoIn {
    private Long reservationId;
    private String description;
}
