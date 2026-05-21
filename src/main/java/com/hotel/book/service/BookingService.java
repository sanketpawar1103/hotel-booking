package com.hotel.book.service;

import com.hotel.book.model.Booking;
import com.hotel.book.model.Hotel;
import com.hotel.book.repository.BookingRepository;
import com.hotel.book.repository.Hotelrepository;
import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.responseDTO.BookingResponseDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final Hotelrepository hotelrepository;
    private final BookingRepository bookingRepository;
    private final MongoTemplate mongoTemplate;
    public BookingService(Hotelrepository hotelrepository, BookingRepository bookingRepository, MongoTemplate mongoTemplate) {
        this.hotelrepository = hotelrepository;
        this.bookingRepository = bookingRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void bookRooms(String userID, BookHotelRequestDTO bookHotelRequestDTO) throws  RuntimeException{

        Hotel hotel = hotelrepository.getHotelById(bookHotelRequestDTO.hotel_id());

        int available = hotel.totalRooms() - hotel.roomsBooked();
        boolean areRoomsAvailable = available >= bookHotelRequestDTO.rooms();

        if (areRoomsAvailable) {
            Booking booking = new Booking(null, userID, bookHotelRequestDTO.hotel_id(), bookHotelRequestDTO.rooms());
            updateRoomsAvailable(bookHotelRequestDTO);
            bookingRepository.save(booking);
            return;
        }

        throw  new RuntimeException("those many rooms are not left in this hotel");
    }

    private void updateRoomsAvailable(BookHotelRequestDTO bookHotelRequestDTO) {
        Query query = new Query(Criteria.where("id").is(bookHotelRequestDTO.hotel_id()));
        Update update = new Update().inc("roomsBooked", bookHotelRequestDTO.rooms());

        mongoTemplate.updateFirst(query, update, Hotel.class);
    }

    public List<BookingResponseDTO> getBookings(String userID) {
        List<Booking> bookings = bookingRepository.getBookingsByUserID(userID);
        return bookings.stream().map(this::mapToDTO).toList();
    }


    private BookingResponseDTO mapToDTO(Booking booking) {

        return new BookingResponseDTO(
                booking.id(),
                booking.hotelID(),
                booking.noOfRoomsBooked()
        );
    }
}
