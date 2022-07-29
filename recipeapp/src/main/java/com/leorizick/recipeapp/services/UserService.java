package com.leorizick.recipeapp.services;


import com.leorizick.recipeapp.dto.UserDto;
import com.leorizick.recipeapp.entities.User;
import com.leorizick.recipeapp.repositories.UserRepository;
import com.leorizick.recipeapp.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User find(Long id){
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Usuario nao encontrado! Id: " + id));
    }

    public Page<User> findAll(Pageable pageable){
        Page<User> userPage = repository.findAll(pageable);
        return userPage;
    }

    public User findByEmail(String email){
        User user = repository.findByEmail(email);
        return user;
    }

    public User save (UserDto userDto){
        User user = transformDto(userDto);
        return repository.save(user);
    }

    public User update (UserDto userDto){
        User user = transformDto(userDto);
        return repository.save(user);
    }

    public void delete(Long id){
        User user = find(id);
        user.setIsActive(false);
    }

    private User transformDto(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .isActive(true)
                .password(userDto.getPassword())
                .build();
        return user;
    }
}
