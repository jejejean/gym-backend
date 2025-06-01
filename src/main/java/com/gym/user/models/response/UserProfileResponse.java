package com.gym.user.models.response;

import com.gym.shared.interfaces.IHandleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfileResponse implements IHandleResponse {
    private Long id;
    private String sex;
    private Double height;
    private Double weight;
}
