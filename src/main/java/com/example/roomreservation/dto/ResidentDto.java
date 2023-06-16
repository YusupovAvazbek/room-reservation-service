package com.example.roomreservation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDto {
    private Long id;
    @NotBlank
    private String fullName;
    @Pattern(regexp = "\\+998 \\d{2} \\d{3} \\d{2} \\d{2}")
    private String phoneNumber;
    @Email
    private String email;

}
