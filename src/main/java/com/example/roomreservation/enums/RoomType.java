package com.example.roomreservation.enums;

public enum RoomType {
    focus("focus"),
    team("team"),
    conference("conference");
    private String name;
    RoomType(String name){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
