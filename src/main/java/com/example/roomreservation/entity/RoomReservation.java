package com.example.roomreservation.entity;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime start;
    @Column(name = "endTime")
    LocalDateTime end;
    @ManyToOne
    private Room room;
    @ManyToOne
    private Resident resident;
    @CreatedDate
    @Timestamp
    private LocalDateTime createdAt;
}
