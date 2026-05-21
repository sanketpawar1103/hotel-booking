package com.hotel.book.controller;

import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.service.BookingService;
import com.hotel.book.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping("/bookings")
    public ResponseEntity<Map<String, String>> bookHotelRooms(@RequestHeader(value = "Authorization", required = false) String authHeader  ,
                                                              @RequestBody BookHotelRequestDTO bookHotelRequestDTO ) {
        try{
            String username = "default user"; // later we'll extract this using jwt token
            String userID = "0" ; // extract userid from username

            this.bookingService.bookRooms(userID, bookHotelRequestDTO);

            return ResponseEntity.ok(Map.of("message", "Booking successful"));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("message" , e.getMessage()));
        }

    }

    @GetMapping("/bookings")
    public  void getBookings(@RequestHeader(value = "Authorization", required = false) String authHeader  ,
                             @RequestBody BookHotelRequestDTO bookHotelRequestDTO ) {

        String username = "default user"; // later we'll extract this using jwt token
        String userID = "0" ; // extract userid from username
    }
}
