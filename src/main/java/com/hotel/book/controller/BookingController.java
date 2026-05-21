package com.hotel.book.controller;

import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.responseDTO.BookingResponseDTO;
import com.hotel.book.security.JwtService;
import com.hotel.book.service.BookingService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookingController {
    private final BookingService bookingService;
    private final JwtService jwtService;

    public BookingController(BookingService bookingService, JwtService jwtService) {
        this.bookingService = bookingService;
        this.jwtService = jwtService;
    }

    @PostMapping("/bookings")
    public ResponseEntity<Map<String, String>> bookHotelRooms(@RequestHeader(value = "Authorization") String authHeader  ,
                                                              @RequestBody BookHotelRequestDTO bookHotelRequestDTO ) {
        try{
            String userId = getUserId(authHeader);
            this.bookingService.bookRooms(userId, bookHotelRequestDTO);

            return ResponseEntity.ok(Map.of("message", "Booking successful"));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("message" , e.getMessage()));
        }

    }

    @GetMapping("/bookings")
    public List<BookingResponseDTO> getBookings(@RequestHeader(value = "Authorization") String authHeader) {
        try {
            String userId = getUserId(authHeader);
            return bookingService.getBookings(userId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getUserId(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtService.extractUserId(token);
        }
        throw new RuntimeException("Unauthorized user. Login first");
    }

    @GetMapping("/bookings/{id}/receipt.pdf")
    public ResponseEntity<?> getReceipt(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=receipt.pdf");
        ByteArrayInputStream pdfStream = bookingService.generateReceiptPdf(id);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}