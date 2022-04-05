package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.configuration.JwtTokenUtil;
import com.example.nnpia_sem_backend.dto.LoginDto;
import com.example.nnpia_sem_backend.entity.ApiResponse;
import com.example.nnpia_sem_backend.entity.AuthToken;
import com.example.nnpia_sem_backend.entity.Login;
import com.example.nnpia_sem_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> generateToken(@RequestBody LoginDto loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final Login login = loginService.findByUsername(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(login);
        return new ApiResponse<>(200, "success",new AuthToken(token, login.getUserName(), login.getId()));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ApiResponse<Void> logout() throws AuthenticationException {
        return new ApiResponse<>(200, "success",null);
    }

}
