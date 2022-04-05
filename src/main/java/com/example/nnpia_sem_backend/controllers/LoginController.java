package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.dto.LoginEditDto;
import com.example.nnpia_sem_backend.entity.ApiResponse;
import com.example.nnpia_sem_backend.entity.Login;
import com.example.nnpia_sem_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @GetMapping
    public List<Login> getAll() {
        return loginService.findAll();
    }


    @PutMapping
    public ApiResponse<Boolean> updatePassword(@RequestBody LoginEditDto user) {
        System.out.println("put");
        if (loginService.updatePassword(user)){
            return new ApiResponse<>(HttpStatus.OK.value(), "", "User updated successfully.");
        }else{
            return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Wrong password.", false);
        }

    }
}
