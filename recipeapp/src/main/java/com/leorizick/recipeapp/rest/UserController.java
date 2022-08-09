package com.leorizick.recipeapp.rest;

import com.leorizick.recipeapp.dto.UserDto;
import com.leorizick.recipeapp.entities.User;
import com.leorizick.recipeapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> find(@PathVariable Long id){
        User user = service.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<User>> findAll(Pageable pageable){
        Page<User> userPage = service.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userPage);
    }

    @GetMapping("/email/{email}")
    @ResponseBody
    public ResponseEntity<User> findByEmail(@PathVariable String email){
        User user = service.findByEmail(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<User> save (UserDto userDto){
        User user = service.save(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PutMapping
    public ResponseEntity<User> update(UserDto userDto){
        User user = service.update(userDto);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete (Long id){
        service.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
