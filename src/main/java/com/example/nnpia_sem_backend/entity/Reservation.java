package com.example.nnpia_sem_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date reservationDate;

    @NotNull
    private ReservationStatus status;

    @NotNull
    @Column
    @Email(message = "Email should be valid")
    private String email;

    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private BeautyProcedure beautyProcedure;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    @JsonIgnore
    private BeautySalon beautySalon;

}
