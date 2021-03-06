package com.example.nnpia_sem_backend.controllers;

import com.example.nnpia_sem_backend.dto.ProcedureCreateDto;
import com.example.nnpia_sem_backend.dto.ProcedureDto;
import com.example.nnpia_sem_backend.entity.ApiResponse;
import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.service.ProcedureService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class ProcedureController {
    private final ProcedureService procedureService;
    private final ModelMapper modelMapper;

    public ProcedureController(ProcedureService procedureService, ModelMapper modelMapper) {
        this.procedureService = procedureService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/public/procedures")
    public ApiResponse<List<ProcedureDto>> getAll() {
        List<BeautyProcedure> beautyProcedures = procedureService.findAll();
        return new ApiResponse<>(HttpStatus.OK.value(), "OK.", beautyProcedures.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @PostMapping("/api/procedure")
    public ApiResponse<ProcedureDto> createProcedure(@RequestBody @Valid ProcedureCreateDto procedureDto) {
        try {
            BeautyProcedure beautyProcedure = convertToEntity(procedureDto);
            ProcedureDto createdProcedure = convertToDto(procedureService.createProcedure(beautyProcedure));
            return new ApiResponse<>(HttpStatus.OK.value(), "Procedure created successfully.", createdProcedure);
        } catch (Exception exception) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Procedure create fail.");
        }
    }

    @PutMapping("/api/procedure")
    public ApiResponse<ProcedureDto> updateProcedure(@RequestBody @Valid ProcedureDto procedureDto) {
        try {
            BeautyProcedure beautyProcedure = convertToEntity(procedureDto);
            ProcedureDto updatedProcedure = convertToDto(procedureService.updateProcedure(beautyProcedure, procedureDto.getId()));
            return new ApiResponse<>(HttpStatus.OK.value(), "Procedure updated successfully.", updatedProcedure);
        } catch (Exception exception) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Update procedure fail");
        }
    }

    @DeleteMapping("/api/procedure")
    public ApiResponse<ProcedureDto> deleteProcedure(Long id) {
        try {
            ProcedureDto deletedProcedure = convertToDto(procedureService.deleteProcedure(id));
            return new ApiResponse<>(HttpStatus.OK.value(), "Procedure deleted successfully.", deletedProcedure);
        } catch (Exception exception) {
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Delete procedure fail. There may be reservations for this procedure.");
        }
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
        beautyProcedure.setDescription(procedureDto.getDescription());
        return beautyProcedure;
    }

    private BeautyProcedure convertToEntity(ProcedureCreateDto procedureDto) {
        BeautyProcedure beautyProcedure = modelMapper.map(procedureDto, BeautyProcedure.class);
        beautyProcedure.setName(procedureDto.getName());
        beautyProcedure.setPrice(procedureDto.getPrice());
        beautyProcedure.setDescription(procedureDto.getDescription());
        return beautyProcedure;
    }
}
