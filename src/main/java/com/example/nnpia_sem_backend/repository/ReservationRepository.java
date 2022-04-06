package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationByReservationDate(Date date);
}
