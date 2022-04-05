package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProcedureService {

    @Autowired
    ProcedureRepository procedureRepository;

    public List<BeautyProcedure> findAll() {
        return procedureRepository.findAll();
    }

    public BeautyProcedure findById(Long id) {
        if (procedureRepository.findById(id).isPresent()) {
            return procedureRepository.findById(id).get();
        } else {
            throw new NoSuchElementException("Senior with ID: " + id + " was not found!");
        }
    }

    public BeautyProcedure findProcedureByName(String name){
        return procedureRepository.findByName(name);
    }

    public void createProcedure(String name, Double price){
        BeautyProcedure beautyProcedureByName = findProcedureByName(name);
        if(beautyProcedureByName == null){
            BeautyProcedure beautyProcedure = new BeautyProcedure();
            beautyProcedure.setName(name);
            beautyProcedure.setPrice(price);
            procedureRepository.save(beautyProcedure);
        }
    }

    public void changePriceOfProcedure(String name, Double price){
        BeautyProcedure beautyProcedure = findProcedureByName(name);
        if (beautyProcedure != null) {
            beautyProcedure.setPrice(price);
            procedureRepository.save(beautyProcedure);
        }
    }
}
