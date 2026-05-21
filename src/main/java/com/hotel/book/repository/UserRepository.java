package com.hotel.book.repository;

import com.hotel.book.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByNameAndPassword(String name, String password);
}
