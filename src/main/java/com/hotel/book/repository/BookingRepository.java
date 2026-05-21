package com.hotel.book.repository;

import com.hotel.book.model.Booking;
import com.hotel.book.responseDTO.BookingResponseDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> getBookingsByUserID(String userID);

    Booking getBookingById(String id);
}
