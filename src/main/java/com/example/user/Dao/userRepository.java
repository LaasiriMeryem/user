package com.example.user.Dao;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.user.entities.User;

public interface userRepository extends MongoRepository<User,String> {

}
