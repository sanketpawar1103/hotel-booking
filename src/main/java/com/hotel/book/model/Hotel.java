package com.hotel.book.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hotels")
public record Hotel (
    @Id
    String id,
    String name,
    Integer roomsBooked,
    Integer totalRooms,
    String city
){};
