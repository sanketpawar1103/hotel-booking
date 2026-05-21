package com.hotel.book.service;

import com.hotel.book.responseDTO.SearchHotelResponse;

import java.util.List;

public interface HotelService {
    List<SearchHotelResponse> search(String city);
}
