package com.hotel.book.mongodbServiceImp;

import com.hotel.book.repository.hotelrepository;
import com.hotel.book.responseDTO.SearchHotelResponse;
import com.hotel.book.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImp implements HotelService {
    private final hotelrepository hotelRepository;

    public HotelServiceImp(hotelrepository hotelRepository) {
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
