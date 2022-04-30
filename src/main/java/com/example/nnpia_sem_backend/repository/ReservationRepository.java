package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationByReservationDateAndStatusIsNot(Date date, ReservationStatus status);

    Boolean existsByBeautyProcedure_Id(Long id);

    Reservation findReservationByBeautyProcedure(BeautyProcedure procedure);
}
