package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProcedureService {

    @Autowired
    ProcedureRepository procedureRepository;

    public List<BeautyProcedure> findAll() {
        return procedureRepository.findAll();
    }

    public BeautyProcedure findById(Long id) {
        Optional<BeautyProcedure> beautyProcedure = procedureRepository.findById(id);
        if (beautyProcedure.isPresent()) {
            return beautyProcedure.get();
        } else {
            throw new NoSuchElementException("Procedure with ID: " + id + " was not found!");
        }
    }

    public Long findIdByName(String name) {
        BeautyProcedure procedure = findProcedureByName(name);
        return procedure.getId();
    }

    public BeautyProcedure findProcedureByName(String name) {
        return procedureRepository.findByName(name);
    }

    public boolean createProcedure(BeautyProcedure beautyProcedure) {
        BeautyProcedure beautyProcedureByName = findProcedureByName(beautyProcedure.getName());
        if (beautyProcedureByName == null) {
            procedureRepository.save(beautyProcedure);
            return true;
        }
        return false;
    }

    public boolean updateProcedure(BeautyProcedure beautyProcedure, Long id) {
        try {
            if (!procedureRepository.findByNameAndIdIsNot(beautyProcedure.getName(), id).isPresent()) {
                BeautyProcedure beautyProcedureById = findById(id);
                if (beautyProcedureById != null) {
                    beautyProcedureById.setName(beautyProcedure.getName());
                    beautyProcedureById.setPrice(beautyProcedure.getPrice());
                    procedureRepository.save(beautyProcedureById);
                    return true;
                }
            }
            return false;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public boolean deleteProcedure(Long id) {
        try {
            BeautyProcedure beautyProcedure = findById(id);
            if (beautyProcedure != null) {
                procedureRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }
}
