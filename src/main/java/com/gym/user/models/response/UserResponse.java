package com.gym.user.models.response;

import com.gym.shared.interfaces.IHandleResponse;
import com.gym.userPlans.models.response.UserPlanResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse implements IHandleResponse {
    private Long idUser;
    private String username;
    private String email;
    private String roles;
    private String phone;
    private String status;
    private String userType;
    private UserProfileResponse userProfileResponse;
    private List<UserPlanResponse> userPlansResponse;

}
