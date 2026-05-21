package com.hotel.book.mongodbServiceImp;

import com.hotel.book.model.Booking;
import com.hotel.book.model.Hotel;
import com.hotel.book.repository.BookingRepository;
import com.hotel.book.repository.hotelrepository;
import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.responseDTO.SearchHotelResponse;
import com.hotel.book.service.HotelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImp implements HotelService {
    private final hotelrepository hotelRepository;
    private  final BookingRepository bookingRepository;
    public HotelServiceImp(hotelrepository hotelRepository, BookingRepository bookingRepository) {
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


    @Override
    public void bookRooms(String userID, BookHotelRequestDTO bookHotelRequestDTO) throws  RuntimeException{

        Hotel hotel = hotelRepository.getHotelById(bookHotelRequestDTO.hotel_id());
        Boolean areRoomsAvailable = hotel.roomsBooked() < hotel.totalRooms();
//        Boolean areRoomsAvailable = true;
        if(areRoomsAvailable) {
            Booking booking = new Booking(null, userID, bookHotelRequestDTO.hotel_id(), bookHotelRequestDTO.rooms());
            bookingRepository.save(booking);
            return;
        }

        throw  new RuntimeException("no rooms left in this hotel");
    }

}
