package com.hotel.book.service;

import com.hotel.book.model.Booking;
import com.hotel.book.model.Hotel;
import com.hotel.book.model.User;
import com.hotel.book.repository.BookingRepository;
import com.hotel.book.repository.HotelRepository;
import com.hotel.book.repository.UserRepository;
import com.hotel.book.requestDTO.BookHotelRequestDTO;
import com.hotel.book.responseDTO.BookingResponseDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class BookingService {
    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public BookingService(HotelRepository hotelRepository,
                          BookingRepository bookingRepository, UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void bookRooms(String userID, BookHotelRequestDTO bookHotelRequestDTO) throws  RuntimeException{

        Hotel hotel =
                hotelRepository.getHotelById(bookHotelRequestDTO.hotel_id());

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

    public ByteArrayInputStream generateReceiptPdf(String id) {
        Booking booking = bookingRepository.getBookingById(id);
        User user = userRepository.getUserById(booking.userID());
        Hotel hotel = hotelRepository.getHotelById(booking.hotelID());

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            generateDocument(document, booking, user, hotel);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error creating the pdf file", e);
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void generateDocument(Document document, Booking booking, User user, Hotel hotel) {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Paragraph title = new Paragraph("BOOKING RECEIPT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        document.add(new Paragraph("Booking ID: " + booking.id(), bodyFont));
        document.add(new Paragraph("User: " + user.name(), bodyFont));
        document.add(new Paragraph("Hotel: " + hotel.name(), bodyFont));
        document.add(new Paragraph("Rooms Booked: " + booking.noOfRoomsBooked(),
                bodyFont));
    }
}
