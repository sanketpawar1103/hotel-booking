package com.hotel.book.mongodbServiceImp;

import com.hotel.book.repository.BookingRepository;
import com.hotel.book.repository.Hotelrepository;
import com.hotel.book.responseDTO.SearchHotelResponse;
import com.hotel.book.service.HotelService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HotelServiceImp implements HotelService {
    private final Hotelrepository hotelRepository;
    private  final BookingRepository bookingRepository;

    public HotelServiceImp(Hotelrepository hotelRepository, BookingRepository bookingRepository) {
        this.hotelRepository = hotelRepository;

        this.bookingRepository = bookingRepository;

    }

    @Override
    public List<SearchHotelResponse> search(String city) {
        return hotelRepository.findByCity(city)
                .stream()
                .map(hotel ->
                        new SearchHotelResponse(hotel.id(), hotel.name(), hotel.city())
                ).toList();
    }

}
