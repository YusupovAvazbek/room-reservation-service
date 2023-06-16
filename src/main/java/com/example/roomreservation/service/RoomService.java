package com.example.roomreservation.service;

import com.example.roomreservation.dto.ApiResult;
import com.example.roomreservation.dto.BookingDto;
import com.example.roomreservation.dto.RoomDto;
import com.example.roomreservation.additional.AvailableRoom;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    ResponseEntity<ApiResult<RoomDto>> addRoom(RoomDto roomDto);
    ResponseEntity<ApiResult<Page<RoomDto>>> getAllRooms(String search, String type, int page, int size);
    ResponseEntity<ApiResult<RoomDto>> getRoomById(Long id);
    ResponseEntity<ApiResult<List<AvailableRoom>>> getRoomByIdAvailability(Long id, LocalDate date);
    ResponseEntity<String> bookingRoom(Long id,BookingDto bookingDto);
}
