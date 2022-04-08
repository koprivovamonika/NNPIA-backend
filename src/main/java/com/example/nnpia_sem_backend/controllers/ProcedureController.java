package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.dto.ProcedureDto;
import com.example.nnpia_sem_backend.entity.ApiResponse;
import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.service.ProcedureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class ProcedureController {
    @Autowired
    ProcedureService procedureService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/public/procedures")
    public List<ProcedureDto> getAll() {
        List<BeautyProcedure> beautyProcedures = procedureService.findAll();
        return beautyProcedures.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/api/procedure")
    public ApiResponse<Boolean> createProcedure(@RequestBody @Valid ProcedureDto procedureDto) throws ParseException {
        BeautyProcedure beautyProcedure = convertToEntity(procedureDto);
        if (procedureService.createProcedure(beautyProcedure)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "Procedure created successfully.", true);
        }
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Create procedure fail.", false);
    }

    @PutMapping("/api/procedure")
    public ApiResponse<Boolean> updateProcedure(@RequestBody @Valid ProcedureDto procedureDto, Long id) throws ParseException {
        BeautyProcedure beautyProcedure = convertToEntity(procedureDto);
        if (procedureService.updateProcedure(beautyProcedure, id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "Procedure updated successfully.", true);
        }
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Update procedure fail.", false);
    }

    @DeleteMapping("/api/procedure")
    public ApiResponse<Boolean> deleteProcedure(Long id) {
        if (procedureService.deleteProcedure(id)) {
            return new ApiResponse<>(HttpStatus.OK.value(), "Procedure deleted successfully.", true);
        }
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), "Delete procedure fail.", false);
    }


    private ProcedureDto convertToDto(BeautyProcedure beautyProcedure) {
        ProcedureDto procedureDto = modelMapper.map(beautyProcedure, ProcedureDto.class);
        procedureDto.setName(beautyProcedure.getName());
        procedureDto.setPrice(beautyProcedure.getPrice());
        return procedureDto;
    }

    private BeautyProcedure convertToEntity(ProcedureDto procedureDto) {
        BeautyProcedure beautyProcedure = modelMapper.map(procedureDto, BeautyProcedure.class);
        beautyProcedure.setName(procedureDto.getName());
        beautyProcedure.setPrice(procedureDto.getPrice());
        return beautyProcedure;
    }
}
