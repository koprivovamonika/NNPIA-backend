package com.example.nnpia_sem_backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Login {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String userName;

    @NotNull
    @Column
    private String password;

    @OneToOne(mappedBy = "login")
    private BeautySalon beautySalon;

}
