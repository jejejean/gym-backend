package com.gym.user.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfileRequest implements IHandleRequest {
    private Long id;
    private Long userId;
    private String sex;
    private Double height;
    private Double weight;
}
