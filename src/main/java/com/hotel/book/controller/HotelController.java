package com.hotel.book.controller;

import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.requestDTO.SearchCriteria;
import com.hotel.book.responseDTO.SearchHotelResponse;
import com.hotel.book.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HotelController {
    private static final Logger logger = LoggerFactory.getLogger(HotelController.class);
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/search/hotels")
    public List<SearchHotelResponse> searchHotels(SearchCriteria searchCriteria) {
        logger.info(searchCriteria.city());
        String city = searchCriteria.city();
        return hotelService.search(city);
    }

    @PostMapping("/bookings")
    public ResponseEntity<Map<String, String>> bookHotelRooms(@RequestHeader(value = "Authorization", required = false) String authHeader  ,
                                                              @RequestBody BookHotelRequestDTO bookHotelRequestDTO ) {

        try{
            String username = "default user"; // later we'll extract this using jwt token
            String userID = "0" ; // extract userid from username

            this.hotelService.bookRooms(userID, bookHotelRequestDTO);

            return ResponseEntity.ok(Map.of("message", "Booking successful"));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("message" , e.getMessage()));
        }

    }
}
