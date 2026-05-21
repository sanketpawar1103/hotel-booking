package com.hotel.book.repository;

import com.hotel.book.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface hotelrepository extends MongoRepository<Hotel, String> {
    List<Hotel> findByCity(String city);

    Hotel getHotelById(String id);
}
