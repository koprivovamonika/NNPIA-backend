package com.example.nnpia_sem_backend.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeSlotDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isSlotFree;

    public TimeSlotDto(LocalTime startTime, LocalTime endTime, boolean isSlotFree) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isSlotFree = isSlotFree;
    }
}
