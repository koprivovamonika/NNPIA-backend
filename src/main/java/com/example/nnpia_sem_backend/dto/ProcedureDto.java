package com.example.nnpia_sem_backend.dto;

import lombok.Data;

@Data
public class ProcedureDto {
    private Long id;
    private String name;
    private Double price;
    private String description;
}
