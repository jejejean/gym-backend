package com.gym.user.interfaces;

import com.gym.user.models.request.UserRequest;
import com.gym.user.models.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse updateUserPlan(Long id, UserRequest request);
    List<UserResponse> findAllByUserType();
    void updatePassword(Long userId, String newPassword, String confirmPassword);
}
