package com.epam.userservice.service;

import com.epam.userservice.dao.User;

import java.util.List;

public interface UserServiceInterface {
    List<User> findUsers();

    User addUser(User requestUser);

    User findUserById(Long id) throws RuntimeException;

    User updateUser(User requestUser) throws RuntimeException;

    void deleteUser(Long id) throws RuntimeException;
}
