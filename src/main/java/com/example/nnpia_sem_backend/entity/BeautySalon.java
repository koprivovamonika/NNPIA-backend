package com.example.nnpia_sem_backend.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
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
    @Temporal(TemporalType.TIME)
    private Date openingTime;

    @NotNull
    @Temporal(TemporalType.TIME)
    private Date closingTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Login login;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private Set<BeautyProcedure> beautyProcedures;

}
