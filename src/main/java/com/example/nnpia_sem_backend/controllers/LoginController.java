package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.dto.LoginEditDto;
import com.example.nnpia_sem_backend.entity.ApiResponse;
import com.example.nnpia_sem_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PutMapping
    public ApiResponse<Boolean> updatePassword(@RequestBody LoginEditDto user) {
        if (loginService.updatePassword(user)){
            return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", true);
        }else{
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Wrong password.", false);
        }

    }
}
