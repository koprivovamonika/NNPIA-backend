package com.example.nnpia_sem_backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import javax.validation.constraints.Email;

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
    @Column
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private BeautyProcedure beautyProcedure;

    @ManyToOne
    @JoinColumn(name = "salon_id")
    private BeautySalon beautySalon;

}
