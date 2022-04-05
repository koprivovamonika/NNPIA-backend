package com.example.nnpia_sem_backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.Email;

@Data
@Entity
public class Reservation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date endTime;

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
