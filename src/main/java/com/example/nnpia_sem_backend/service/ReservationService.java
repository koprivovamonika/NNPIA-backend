package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    public boolean createReservation(Reservation reservation){
        if(reservationRepository.save(reservation) != null){
            return true;
        }
        return false;
    }
}
