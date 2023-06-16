package com.example.roomreservation.service.mapper;

import com.example.roomreservation.dto.ResidentDto;
import com.example.roomreservation.entity.Resident;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentMapper extends CommonMapper<Resident, ResidentDto> {
}
