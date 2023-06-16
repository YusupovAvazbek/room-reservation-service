package com.example.roomreservation.service.mapper;

public interface CommonMapper<E,D> {
    D toDto(E e);
    E toEntity(D d);

}
