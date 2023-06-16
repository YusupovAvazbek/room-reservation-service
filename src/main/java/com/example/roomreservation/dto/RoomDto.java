package com.example.roomreservation.dto;

import com.example.roomreservation.enums.RoomType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private RoomType type;
    @NotNull
    @Size(min = 1,max = 50)
    private int capacity;
}
