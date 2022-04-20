package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.dto.TimeSlotDto;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import com.example.nnpia_sem_backend.repository.ReservationPagingRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final EmailService emailService;
    private final ReservationPagingRepository reservationPagingRepository;

    public ReservationService(ReservationRepository reservationRepository, EmailService emailService, ReservationPagingRepository reservationPagingRepository) {
        this.reservationRepository = reservationRepository;
        this.emailService = emailService;
        this.reservationPagingRepository = reservationPagingRepository;
    }

    public boolean createReservation(Reservation reservation) {
        List<Reservation> reservations = reservationRepository.findReservationByReservationDateAndStatusIsNot(reservation.getReservationDate(), ReservationStatus.DELETED);

        boolean canSave = true;
        for (Reservation reservationFromDb : reservations) {
            if(!(reservation.getStartTime().plusMinutes(1).isBefore(reservationFromDb.getStartTime()) && reservation.getEndTime().minusMinutes(1).isBefore(reservationFromDb.getStartTime()) ) &&
                    !(reservation.getStartTime().plusMinutes(1).isAfter(reservationFromDb.getEndTime()) && reservation.getEndTime().minusMinutes(1).isAfter(reservationFromDb.getEndTime()))){
                canSave = false;
                break;
            }
        }

        if(canSave){
            reservationRepository.save(reservation);
            sendEmail(reservation, "Confirmation of reservation.", "Hello!\nYour reservation has been created. Please wait until you receive a confirmation email from us.");
            return true;
        }else{
            return false;
        }

    }

    public boolean confirmReservation(Long id) {
        try {
            Reservation reservation = findById(id);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            sendEmail(reservation, "Confirmation of reservation.", "Hello!\nWe confirm your reservation in our beauty salon.\nWe look forward to seeing you on " + reservation.getReservationDate() + " at " + reservation.getStartTime());
            reservationRepository.save(reservation);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public boolean setAsDone(Long id) {
        try {
            Reservation reservation = findById(id);
            reservation.setStatus(ReservationStatus.DONE);
            reservationRepository.save(reservation);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public boolean cancelReservation(Long id, String description) {
        try {
            Reservation reservation = findById(id);
            reservation.setStatus(ReservationStatus.DELETED);
            sendEmail(reservation, "Your reservation was cancelled.", "Hello!\nWe apologize, but your reservation at our beauty salon has been cancelled.\nThe reason is: " + description);
            reservationRepository.save(reservation);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public Page<Reservation> getReservationByDateAndStatus(Pageable paging, Long salonId, Date reservationDate, ReservationStatus status) {
        return reservationPagingRepository.findAllByBeautySalon_IdAndReservationDateAndStatus(salonId, reservationDate, status, paging);
    }

    public Page<Reservation> getReservationByDate(Pageable paging, Long salonId, Date reservationDate) {
        return reservationPagingRepository.findAllByBeautySalon_IdAndReservationDateAndStatusIsNot(salonId, reservationDate, paging, ReservationStatus.DELETED);
    }

    private void sendEmail(Reservation reservation, String subject, String text) {
        Thread t = new Thread(() -> {
            try {
                emailService.sendSimpleMessage(reservation.getEmail(), subject,
                        text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    private Reservation findById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            return reservation.get();
        } else {
            throw new NoSuchElementException("Reservation with ID: " + id + " was not found!");
        }
    }
}
