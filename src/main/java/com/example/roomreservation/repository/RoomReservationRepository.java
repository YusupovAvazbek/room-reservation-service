package com.example.roomreservation.repository;

import com.example.roomreservation.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    @Query("select rr from RoomReservation rr where rr.room.id = :rid and rr.start >= :startOfDay and rr.end <= :endOfDay order by rr.start")
    List<RoomReservation> getRoomReservationTimes(@Param("rid") Long rid,
                                                  @Param("startOfDay")LocalDateTime startOfDay,
                                                  @Param("endOfDay") LocalDateTime endOfDay);
}
