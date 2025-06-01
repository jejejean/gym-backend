package com.gym.user.interfaces;


import com.gym.user.models.entity.User;

public interface GetUserData {
    User getUserByUsername(String username);
}