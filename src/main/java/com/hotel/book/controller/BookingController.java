package com.hotel.book.controller;

import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.responseDTO.BookingResponseDTO;
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
    public List<BookingResponseDTO> getBookings(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        String username = "default user"; // later we'll extract this using jwt token
        String userID = "0" ; // extract userid from username

        List<BookingResponseDTO> bookings = bookingService.getBookings(userID);

        return bookings;
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
