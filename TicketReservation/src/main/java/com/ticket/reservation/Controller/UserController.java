package com.ticket.reservation.Controller;

import com.ticket.reservation.Repository.UserRepository;
import com.ticket.reservation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository repository;
    @GetMapping(value = "/")
        public String health(){return "health check success";}
    @PostMapping(value = "/")
    public ResponseEntity<User> addUser(@RequestBody User user){
        user = repository.save(user);
        return  ResponseEntity.ok(user);
    }
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> addUser(){
        return  ResponseEntity.ok(repository.findAll());
    }
}
