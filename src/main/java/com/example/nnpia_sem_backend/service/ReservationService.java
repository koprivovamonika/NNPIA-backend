package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.dto.ReservationPagingDto;
import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import com.example.nnpia_sem_backend.repository.ReservationPagingRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ReservationPagingRepository reservationPagingRepository;

    public boolean createReservation(Reservation reservation){
        reservationRepository.save(reservation);
        sendEmail(reservation, "Confirmation of reservation.", "Hello!\nYour reservation has been created. Please wait until you receive a confirmation email from us.");
        return true;
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
            sendEmail(reservation, "Your reservation was cancelled.", "Hello!\nWe apologize, but your reservation at our beauty salon has been cancelled.\nThe reason is: " + description);
            reservationRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public Page<Reservation> getReservationByDateAndStatus(Pageable paging, Long salonId, Date reservationDate, ReservationStatus status){
        return reservationPagingRepository.findAllByBeautySalon_IdAndReservationDateAndStatus(salonId, reservationDate, status, paging);
    }

    public Page<Reservation> getReservationByDate(Pageable paging, Long salonId, Date reservationDate){
        return reservationPagingRepository.findAllByBeautySalon_IdAndReservationDate(salonId, reservationDate, paging);
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
