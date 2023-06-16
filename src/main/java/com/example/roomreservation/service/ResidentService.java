package com.example.roomreservation.service;

import com.example.roomreservation.dto.ApiResult;
import com.example.roomreservation.dto.ResidentDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ResidentService {
    ResponseEntity<ApiResult<ResidentDto>> addResident(ResidentDto residentDto);
    ResponseEntity<ApiResult<Page<ResidentDto>>> getAllResidents(int page, int size);
    ResponseEntity<ApiResult<ResidentDto>> getResidentById(Long id);
}
