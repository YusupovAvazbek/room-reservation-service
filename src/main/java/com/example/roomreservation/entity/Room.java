package com.example.roomreservation.entity;

import com.example.roomreservation.enums.RoomType;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private RoomType type;
    private int capacity;
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    private List<RoomReservation> roomReservation;
    @CreatedDate
    @Timestamp
    private LocalDateTime createdAt;
}
