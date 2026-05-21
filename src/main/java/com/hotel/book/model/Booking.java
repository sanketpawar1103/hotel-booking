package com.hotel.book.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookings")
public record Booking(
        @Id
        String id,
        String userID,
        String hotelID,
        Integer noOfRoomsBooked

) {
}
