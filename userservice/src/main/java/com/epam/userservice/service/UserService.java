package com.epam.userservice.service;

import com.epam.userservice.dao.ErrorResponse;
import com.epam.userservice.exceptions.UserNotFoundException;
import com.epam.userservice.repository.UserRepository;
import com.epam.userservice.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Class having all service methods
@Service
public class UserService implements UserServiceInterface {
    @Autowired
    UserRepository userRepository;

    @Autowired
    User user;

    /**
     * Method which will retrieve all the Users
     *
     * @return UsersList List
     */
    @Override
    public List<User> findUsers() {
        return (List) userRepository.findAll();
    }

    /**
     * This Method will add a given User to Repository
     *
     * @param requestUser User
     * @return NewUser User
     */
    @Override
    public User addUser(User requestUser) {
        return userRepository.save(requestUser);
    }

    /**
     * This Method will retrieve a User based on the given Id.If User not found will throw Runtime Exception
     *
     * @param id Long
     * @return User User
     * @throws UserNotFoundException
     */
    @Override
    public User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No User with the given Id is available"));
    }

    /**
     * This method will update the User Object attributes with the attributes given in Input
     *
     * @param requestUser User
     * @return updatedUser User
     * @throws UserNotFoundException
     */
    @Override
    public User updateUser(User requestUser) throws UserNotFoundException {
        User user;
        if (userRepository.existsById(requestUser.getId())) {
            user = findUserById(requestUser.getId());
            user.setTitle(requestUser.getTitle());
            user.setName(requestUser.getName());
        } else {
            user = userRepository.save(requestUser);
        }
        return addUser(user);
    }

    /**
     * This method will delete a User based on the given id
     *
     * @param id Long
     * @throws UserNotFoundException
     */
    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("No User with the given Id is available");
        }
    }




}
