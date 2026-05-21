package com.hotel.book.repository;

import com.hotel.book.model.Hotel;
import com.hotel.book.responseDTO.BookingList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {
    List<Hotel> findByCity(String city);
    Hotel getHotelById(String id);
}
