package com.example.nnpia_sem_backend.repository;

import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservationPagingRepository extends PagingAndSortingRepository<Reservation, Long> {
    Page<Reservation> findAllByBeautySalon_IdAndReservationDateAndStatus(Long salonId, Date reservationDate,ReservationStatus status, Pageable pageVariable);
}
