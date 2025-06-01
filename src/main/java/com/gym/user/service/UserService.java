package com.gym.user.service;

import com.gym.exceptions.NotFoundException;
import com.gym.user.interfaces.GetUserData;
import com.gym.user.models.entity.User;
import com.gym.user.repository.UserRepository;
import com.gym.utils.Messages;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements GetUserData {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findUserEntityByEmail(username);
        if(optionalUser.isEmpty()) {
            throw new NotFoundException(Messages.USER_NOT_FOUND.getMessage());
        }
        return optionalUser.get();
    }
}
