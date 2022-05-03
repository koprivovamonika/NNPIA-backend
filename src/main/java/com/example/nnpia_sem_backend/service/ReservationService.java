package com.example.nnpia_sem_backend.service;

import com.example.nnpia_sem_backend.entity.Reservation;
import com.example.nnpia_sem_backend.entity.ReservationStatus;
import com.example.nnpia_sem_backend.repository.ReservationPagingRepository;
import com.example.nnpia_sem_backend.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Reservation createReservation(Reservation reservation) throws Exception {
        List<Reservation> reservations = reservationRepository.findReservationByReservationDateAndStatusIsNot(reservation.getReservationDate(), ReservationStatus.DELETED);
        if (controlTime(reservation, reservations)) {
            reservationRepository.save(reservation);
            sendEmail(reservation, "Confirmation of reservation.", "Hello!\nYour reservation has been created. Please wait until you receive a confirmation email from us.");
            return reservation;
        } else {
            throw new Exception("This time slot is already reserved");
        }

    }

    public Reservation confirmReservation(Long id) {
        Reservation reservation = findById(id);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        sendEmail(reservation, "Confirmation of reservation.", "Hello!\nWe confirm your reservation in our beauty salon.\nWe look forward to seeing you on " + reservation.getReservationDate() + " at " + reservation.getStartTime());
        return reservationRepository.save(reservation);
    }

    public Reservation setAsDone(Long id) {
        Reservation reservation = findById(id);
        reservation.setStatus(ReservationStatus.DONE);
        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(Long id, String description) {
        Reservation reservation = findById(id);
        reservation.setStatus(ReservationStatus.DELETED);
        sendEmail(reservation, "Your reservation was cancelled.", "Hello!\nWe apologize, but your reservation at our beauty salon has been cancelled.\nThe reason is: " + description);
        return reservationRepository.save(reservation);
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

    private boolean controlTime(Reservation reservation, List<Reservation> reservations) {
        boolean canSave = true;
        for (Reservation reservationFromDb : reservations) {
            if (!(reservation.getStartTime().plusMinutes(1).isBefore(reservationFromDb.getStartTime()) && reservation.getEndTime().minusMinutes(1).isBefore(reservationFromDb.getStartTime())) &&
                    !(reservation.getStartTime().plusMinutes(1).isAfter(reservationFromDb.getEndTime()) && reservation.getEndTime().minusMinutes(1).isAfter(reservationFromDb.getEndTime()))) {
                canSave = false;
                break;
            }
        }
        return canSave;
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
