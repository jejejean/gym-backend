package com.gym.user.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdatePasswordRequest {
    private Long userId;
    private String confirmPassword;
    private String newPassword;
}
