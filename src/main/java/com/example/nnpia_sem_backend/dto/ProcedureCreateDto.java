package com.example.nnpia_sem_backend.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ProcedureCreateDto {
    @NotNull
    private String name;
    @NotNull
    @Min(value = 0, message = "Price can not be minus")
    private Double price;
    private String description;
}
