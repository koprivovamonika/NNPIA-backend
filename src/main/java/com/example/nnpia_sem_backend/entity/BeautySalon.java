package com.example.nnpia_sem_backend.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "salon")
public class BeautySalon {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    private LocalTime openingTime;

    @NotNull
    private LocalTime closingTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Login login;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private Set<BeautyProcedure> beautyProcedures;

}
