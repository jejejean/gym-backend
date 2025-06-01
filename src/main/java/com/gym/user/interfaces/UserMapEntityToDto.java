package com.gym.user.interfaces;


import com.gym.user.models.entity.User;
import com.gym.user.models.response.UserResponse;

public interface UserMapEntityToDto {
    UserResponse entityToDto(User user);
}