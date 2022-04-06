package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.dto.ProcedureDto;
import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.entity.Login;
import com.example.nnpia_sem_backend.service.ProcedureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/public/procedures")
public class ProcedureController {
    @Autowired
    ProcedureService procedureService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProcedureDto> getAll() {
        List<BeautyProcedure> beautyProcedures = procedureService.findAll();
        return beautyProcedures.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ProcedureDto convertToDto(BeautyProcedure beautyProcedure) {
        ProcedureDto procedureDto = modelMapper.map(beautyProcedure, ProcedureDto.class);
        procedureDto.setName(beautyProcedure.getName());
        procedureDto.setPrice(beautyProcedure.getPrice());
        return procedureDto;
    }
}
