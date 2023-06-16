package com.example.roomreservation.controller;

import com.example.roomreservation.dto.ApiResult;
import com.example.roomreservation.dto.BookingDto;
import com.example.roomreservation.dto.RoomDto;
import com.example.roomreservation.additional.AvailableRoom;
import com.example.roomreservation.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    @GetMapping
    @Operation(
            summary = "get all rooms with search, type filter and pagination",
            description = "Endpoint to get all rooms.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Room successfully added",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResult.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResult<Page<RoomDto>>> getRooms(@RequestParam(required = false) String search,
                                                             @RequestParam(required = false) String type,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        return roomService.getAllRooms(search,type,page,size);
    }
    @PostMapping
    @Operation(
            summary = "Add a new room",
            description = "Endpoint to add a new room.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Room successfully added",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResult.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResult<RoomDto>> addRoom(@RequestBody RoomDto roomDto){
        return roomService.addRoom(roomDto);
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "get room by id",
            description = "Endpoint to get room by id.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Room successfully added",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResult.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResult<RoomDto>> getRoomById(@PathVariable Long id) {

        return roomService.getRoomById(id);
    }
    @GetMapping("/{id}/availability")
    @Operation(
            summary = "to see the availability of the rooms according to the given date",
            description = "Endpoint to see the availability of the rooms according to the given date.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Room successfully added",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResult.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResult<List<AvailableRoom>>> getRoomAvailability(@PathVariable Long id,
                                                                              @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return roomService.getRoomByIdAvailability(id,date);
    }
    @PostMapping("/{id}/book")
    @Operation(
            summary = "booking the rooms according to the given time",
            description = "Endpoint to booking the rooms according to the given time.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoomDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Room successfully added",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResult.class)
                            )
                    )
            }


    )
    public ResponseEntity<String> bookRoom(@PathVariable Long id,
                                           @RequestBody BookingDto bookingRequest) {
        return roomService.bookingRoom(id, bookingRequest);
    }
}
