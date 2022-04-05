package com.example.nnpia_sem_backend.dto;

import lombok.Data;

@Data
public class LoginEditDto {
    private String username;
    private String passwordOld;
    private String passwordNew;
}
