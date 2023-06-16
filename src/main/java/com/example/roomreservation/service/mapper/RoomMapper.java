package com.example.roomreservation.service.mapper;

import com.example.roomreservation.dto.RoomDto;
import com.example.roomreservation.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper extends CommonMapper<Room, RoomDto>{
}
