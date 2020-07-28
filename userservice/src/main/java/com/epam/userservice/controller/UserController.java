package com.epam.userservice.controller;

import com.epam.userservice.dao.ErrorResponse;
import com.epam.userservice.dao.Response;
import com.epam.userservice.dao.User;
import com.epam.userservice.exceptions.UserNotFoundException;
import com.epam.userservice.service.UserServiceInterface;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ErrorResponse errorResponse;

    @Autowired
    User user;

    private final String errorMessage = "User details are not found in the database";

    private final String deleteMessage = "User deleted Successfully";

    @GetMapping
    @HystrixCommand(fallbackMethod = "fallBackMethod")
    public ResponseEntity<List<User>> findUsers() {
        List<User> users = userService.findUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User User) {
        User userAdded = userService.addUser(User);
        return ResponseEntity.status(HttpStatus.CREATED).body(userAdded);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> findUserById(@PathVariable Long id) {
        ResponseEntity responseEntity;
        User user = userService.findUserById(id);
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(user);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User User) {
        ResponseEntity responseEntity;
        User updatedUser = userService.updateUser(User);
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        ResponseEntity responseEntity;
        userService.deleteUser(id);
        responseEntity = ResponseEntity.status(HttpStatus.ACCEPTED).body(deleteMessage);
        return responseEntity;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> errorHandler(UserNotFoundException userNotFoundException) {

        errorResponse.setErrorMessage(errorMessage);
        errorResponse.setErrorStatus(HttpStatus.NOT_FOUND);
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    public List<User> fallBackMethod() {
        user.setId(1L);
        user.setTitle("EPAM");
        user.setName("Organization");
        List<User> usersList = Collections.emptyList();
        usersList.add(user);
        return usersList;
    }
}
