package com.hotel.book.mongodbServiceImp;

import com.hotel.book.repository.HotelRepository;
import com.hotel.book.responseDTO.BookingList;
import com.hotel.book.responseDTO.SearchHotelResponse;
import com.hotel.book.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImp implements HotelService {
    private final HotelRepository hotelRepository;

    public HotelServiceImp(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
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
