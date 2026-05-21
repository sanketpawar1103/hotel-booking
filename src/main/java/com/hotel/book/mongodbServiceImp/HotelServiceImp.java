package com.hotel.book.mongodbServiceImp;

import com.hotel.book.model.Booking;
import com.hotel.book.model.Hotel;
import com.hotel.book.repository.BookingRepository;
import com.hotel.book.repository.Hotelrepository;
import com.hotel.book.repository.Hotelrepository;
import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.responseDTO.SearchHotelResponse;
import com.hotel.book.service.HotelService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import java.util.List;

@Service
public class HotelServiceImp implements HotelService {
    private final Hotelrepository hotelRepository;
    private  final BookingRepository bookingRepository;
    private final MongoTemplate mongoTemplate;
    public HotelServiceImp(Hotelrepository hotelRepository, BookingRepository bookingRepository, MongoTemplate mongoTemplate) {
        this.hotelRepository = hotelRepository;

        this.bookingRepository = bookingRepository;
        this.mongoTemplate = mongoTemplate;
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

        updateRoomsAvailable(bookHotelRequestDTO);

        Boolean areRoomsAvailable = hotel.roomsBooked() < hotel.totalRooms();
        if(areRoomsAvailable) {
            Booking booking = new Booking(null, userID, bookHotelRequestDTO.hotel_id(), bookHotelRequestDTO.rooms());
            bookingRepository.save(booking);
            return;
        }

        throw  new RuntimeException("no rooms left in this hotel");
    }

    private void updateRoomsAvailable(BookHotelRequestDTO bookHotelRequestDTO) {
        Query query = new Query(Criteria.where("id").is(bookHotelRequestDTO.hotel_id()));
        Update update = new Update().inc("roomsBooked", -bookHotelRequestDTO.rooms());

        mongoTemplate.updateFirst(query, update, Hotel.class);
    }

}
