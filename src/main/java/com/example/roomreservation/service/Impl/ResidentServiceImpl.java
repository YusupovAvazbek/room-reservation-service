package com.example.roomreservation.service.Impl;

import com.example.roomreservation.additional.StatusCodes;
import com.example.roomreservation.additional.StatusMessages;
import com.example.roomreservation.dto.ApiResult;
import com.example.roomreservation.dto.ErrorData;
import com.example.roomreservation.dto.ResidentDto;
import com.example.roomreservation.dto.RoomDto;
import com.example.roomreservation.entity.Resident;
import com.example.roomreservation.repository.ResidentRepository;
import com.example.roomreservation.service.ResidentService;
import com.example.roomreservation.service.mapper.ResidentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {
    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper;
    @Override
    public ResponseEntity<ApiResult<ResidentDto>> addResident(ResidentDto residentDto) {
        try{
            Resident resident = residentMapper.toEntity(residentDto);
            Resident save = residentRepository.save(resident);
            return ResponseEntity.ok(ApiResult.<ResidentDto>builder()
                    .success(true)
                    .code(StatusCodes.OK_CODE)
                    .message(StatusMessages.OK)
                    .data(residentMapper.toDto(save))
                    .build());
        }catch (Exception e){
            return ResponseEntity.ok(ApiResult.<ResidentDto>builder()
                    .success(true)
                    .errors(List.of(new ErrorData("database",e.getMessage())))
                    .code(StatusCodes.DATABASE_ERROR_CODE)
                    .message(StatusMessages.DATABASE_ERROR+":"+e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<ApiResult<Page<ResidentDto>>> getAllResidents(int page, int size) {
        try {
            Long count = residentRepository.count();

            PageRequest pageRequest = PageRequest.of(
                    (count / size) <= page ?
                            (count % size == 0 ? (int) (count / size) - 1
                                    : (int) (count / size))
                            : page,
                    size);

            Page<ResidentDto> residents = residentRepository.findAll(pageRequest)
                    .map(residentMapper::toDto);

            return ResponseEntity.ok(ApiResult.<Page<ResidentDto>>builder()
                    .success(true)
                    .code(StatusCodes.OK_CODE)
                    .message(StatusMessages.OK)
                    .data(residents)
                    .build());

        }catch (Exception e){
            return ResponseEntity.ok(ApiResult.<Page<ResidentDto>>builder()
                    .success(true)
                    .errors(List.of(new ErrorData("database",e.getMessage())))
                    .code(StatusCodes.DATABASE_ERROR_CODE)
                    .message(StatusMessages.DATABASE_ERROR+":"+e.getMessage())
                    .build());
        }
    }

    @Override
    public ResponseEntity<ApiResult<ResidentDto>> getResidentById(Long id) {
        try {
            return residentRepository.findById(id)
                    .map(resident -> ResponseEntity.ok(ApiResult.<ResidentDto>builder()
                            .success(true)
                            .code(StatusCodes.OK_CODE)
                            .data(residentMapper.toDto(resident))
                            .build()))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResult.<ResidentDto>builder()
                                    .success(true)
                                    .code(StatusCodes.NOT_FOUND_ERROR_CODE)
                                    .message(StatusMessages.NOT_FOUND)
                                    .build()));


        }catch (Exception e){
            return ResponseEntity.ok(ApiResult.<ResidentDto>builder()
                    .success(true)
                    .errors(List.of(new ErrorData("database",e.getMessage())))
                    .code(StatusCodes.DATABASE_ERROR_CODE)
                    .message(StatusMessages.DATABASE_ERROR+":"+e.getMessage())
                    .build());
        }
    }

}
