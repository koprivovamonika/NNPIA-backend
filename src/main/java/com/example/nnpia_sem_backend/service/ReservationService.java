package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.Login;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    public void createReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }
}
