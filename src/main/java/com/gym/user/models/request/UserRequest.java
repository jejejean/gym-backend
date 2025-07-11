package com.gym.user.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import com.gym.userPlans.models.request.UserPlanRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest implements IHandleRequest {
    private Long idUser;
    private String username;
    private String email;
    private String phone;
    private String userType;
    private String status;
    private Boolean active;
    private UserProfileRequest userProfileRequest;
    private List<UserPlanRequest> userPlansRequest;
}
