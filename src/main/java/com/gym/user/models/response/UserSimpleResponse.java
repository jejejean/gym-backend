package com.gym.user.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSimpleResponse {
    private Long idUser;
    private String username;
    private String email;
    private String phone;
    private String status;
    private UserProfileResponse userProfileResponse;
}
