package com.hotel.book.controller;

import com.hotel.book.requestDTO.SearchCriteria;
import com.hotel.book.responseDTO.SearchHotelResponse;
import com.hotel.book.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<SearchHotelResponse> search = hotelService.search(city);
        if(search.isEmpty())
            return List.of(new SearchHotelResponse("--", "--", "--"));
        return search;
    }

}
