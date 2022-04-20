package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.entity.ProcedureStatus;
import com.example.nnpia_sem_backend.repository.ProcedureRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProcedureService {

    protected final ProcedureRepository procedureRepository;
    protected final ReservationRepository reservationRepository;

    public ProcedureService(ProcedureRepository procedureRepository, ReservationRepository reservationRepository) {
        this.procedureRepository = procedureRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<BeautyProcedure> findAll() {
        return procedureRepository.findAllByStatus(ProcedureStatus.ACTIVE);
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
            beautyProcedure.setStatus(ProcedureStatus.ACTIVE);
            procedureRepository.save(beautyProcedure);
            return true;
        } else if (beautyProcedureByName.getStatus() == ProcedureStatus.INACTIVE) {
            beautyProcedure.setId(beautyProcedureByName.getId());
            beautyProcedure.setStatus(ProcedureStatus.ACTIVE);
            procedureRepository.save(beautyProcedure);
            return true;
        }
        return false;
    }

    public boolean updateProcedure(BeautyProcedure beautyProcedure, Long id) {
        try {
            if (procedureRepository.findByNameAndIdIsNot(beautyProcedure.getName(), id).isEmpty()) {
                BeautyProcedure beautyProcedureById = findById(id);
                if (beautyProcedureById != null) {
                    beautyProcedureById.setName(beautyProcedure.getName());
                    beautyProcedureById.setPrice(beautyProcedure.getPrice());
                    beautyProcedureById.setDescription(beautyProcedure.getDescription());
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
                if (reservationRepository.existsByBeautyProcedure_Id(id)) {
                    beautyProcedure.setStatus(ProcedureStatus.INACTIVE);
                    procedureRepository.save(beautyProcedure);
                } else {
                    procedureRepository.deleteById(id);
                }
                return true;
            }
            return false;
        } catch (NoSuchElementException | DataIntegrityViolationException exception) {
            return false;
        }
    }
}
