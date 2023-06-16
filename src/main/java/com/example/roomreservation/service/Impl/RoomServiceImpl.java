package com.example.roomreservation.service.Impl;

import com.example.roomreservation.additional.StatusCodes;
import com.example.roomreservation.additional.StatusMessages;
import com.example.roomreservation.dto.*;
import com.example.roomreservation.entity.Room;
import com.example.roomreservation.entity.RoomReservation;
import com.example.roomreservation.enums.RoomType;
import com.example.roomreservation.additional.AvailableRoom;
import com.example.roomreservation.repository.RoomRepository;
import com.example.roomreservation.repository.RoomReservationRepository;
import com.example.roomreservation.service.RoomService;
import com.example.roomreservation.service.mapper.RoomMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final EntityManager entityManager;
    private final RoomMapper roomMapper;
    private final RoomReservationRepository roomReservationRepository;


    @Override
    public ResponseEntity<ApiResult<RoomDto>> addRoom(RoomDto roomDto) {
        try {
            Room newRoom = roomMapper.toEntity(roomDto);
            Room save = roomRepository.save(newRoom);
            return ResponseEntity.ok(ApiResult.<RoomDto>builder()
                    .success(true)
                    .data(roomMapper.toDto(save))
                    .build());
        }catch (Exception e){
            return ResponseEntity.ok(ApiResult.<RoomDto>builder()
                    .success(true)
                    .errors(List.of(new ErrorData("database",e.getMessage())))
                    .code(StatusCodes.DATABASE_ERROR_CODE)
                    .message(StatusMessages.DATABASE_ERROR+":"+e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<ApiResult<Page<RoomDto>>> getAllRooms(String search, String type, int page, int size) {

        size = Math.max(size, 0);
        page = Math.max(page,0);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> root = criteriaQuery.from(Room.class);
        criteriaQuery.select(root);

        Predicate searchPredicate = null;
        Predicate typePredicate = null;

        if (search != null && !search.isEmpty()) {
            searchPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + search.toLowerCase() + "%");
        }

        if (type != null && !type.isEmpty()) {
            typePredicate = criteriaBuilder.equal(root.get("type"), RoomType.valueOf(type));
        }

        Predicate finalPredicate = null;

        if (searchPredicate != null && typePredicate != null) {
            finalPredicate = criteriaBuilder.and(searchPredicate, typePredicate);
        } else if (searchPredicate != null) {
            finalPredicate = searchPredicate;
        } else if (typePredicate != null) {
            finalPredicate = typePredicate;
        }

        if (finalPredicate != null) {
            criteriaQuery.where(finalPredicate);
        }

        TypedQuery<Room> result = entityManager.createQuery(criteriaQuery);

        long count = result.getResultList().size();


        if (count > 0 && count / size <= page){
            if (count % size == 0) {
                page = (int) count / size - 1;
            } else {
                page = (int) count / size;
            }
        }
        result.setFirstResult(size * page);
        result.setMaxResults(size);

        Page<RoomDto> pagination = new PageImpl<>(result.getResultList(), PageRequest.of(page, size), count).map(roomMapper::toDto);

        return ResponseEntity.ok(ApiResult.<Page<RoomDto>>builder()
                .success(true)
                .data(pagination)
                .build());
    }

    @Override
    public ResponseEntity<ApiResult<RoomDto>> getRoomById(Long id) {
        try {
            if(id == null){
                return ResponseEntity.badRequest().body(ApiResult.<RoomDto>builder()
                        .success(false)
                        .message(StatusMessages.VALIDATION_ERROR)
                        .build());

            }
            Optional<Room> byId = roomRepository.findById(id);
            if(byId.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResult.<RoomDto>builder()
                        .success(true)
                        .message(StatusMessages.NOT_FOUND)
                        .build());

            }
            Room room = byId.get();
            return ResponseEntity.ok(ApiResult.<RoomDto>builder()
                    .data(roomMapper.toDto(room))
                    .success(true)
                    .build());

        }catch (Exception e){
            return ResponseEntity.ok(ApiResult.<RoomDto>builder()
                    .success(true)
                    .errors(List.of(new ErrorData("database",e.getMessage())))
                    .code(StatusCodes.DATABASE_ERROR_CODE)
                    .message(StatusMessages.DATABASE_ERROR+":"+e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<ApiResult<List<AvailableRoom>>> getRoomByIdAvailability(Long id, LocalDate date) {
        if(date == null){
            date = LocalDate.now();
        }
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<RoomReservation> reservations = roomReservationRepository.getRoomReservationTimes(id, startOfDay.minusDays(1),endOfDay.plusDays(1));

        List<AvailableRoom> availableRooms = new ArrayList<>();

        LocalDateTime previousEnd = startOfDay;

        for (RoomReservation reservation : reservations) {

            LocalDateTime currentStart = reservation.getStart();

            if (previousEnd.isBefore(currentStart)) {
                AvailableRoom freeTimeSlot = AvailableRoom.builder()
                        .start(previousEnd)
                        .end(currentStart)
                        .build();
                availableRooms.add(freeTimeSlot);
            }

            previousEnd = reservation.getEnd();
        }

        if (previousEnd.isBefore(endOfDay)) {
            AvailableRoom freeTimeSlot = AvailableRoom.builder()
                    .start(previousEnd)
                    .end(endOfDay)
                    .build();
            availableRooms.add(freeTimeSlot);
        }
        return ResponseEntity.ok(ApiResult.<List<AvailableRoom>>builder()
                .success(true)
                .data(availableRooms)
                .build());

    }

    @Override
    public ResponseEntity<String> bookingRoom(Long id,BookingDto bookingRequest) {
        try {
            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Room not found"));


            LocalDateTime start = bookingRequest.getStart();
            LocalDateTime end = bookingRequest.getEnd();

            if (start.isAfter(end)) {
                return ResponseEntity.badRequest().body("Invalid booking this time range");
            }

            List<RoomReservation> reservations = room.getRoomReservation();
            for (RoomReservation reservation : reservations) {
                if (start.isBefore(reservation.getEnd()) && end.isAfter(reservation.getStart())) {
                    return ResponseEntity.badRequest().body("Room is already booked this time range");
                }
            }

            RoomReservation newReservation =RoomReservation.builder()
                    .start(start)
                    .end(end)
                    .room(room)
                    .build();

            roomReservationRepository.save(newReservation);

            return ResponseEntity.ok("Room booked successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        }
    }
}
