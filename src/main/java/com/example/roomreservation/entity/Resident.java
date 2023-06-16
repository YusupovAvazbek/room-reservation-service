package com.example.roomreservation.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;

import java.util.List;

@Entity
@Table(name = "residents")
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    @OneToMany(mappedBy = "resident", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RoomReservation> roomReservations;
    @CreatedDate
    @Timestamp
    private LocalDateTime createdAt;
}
