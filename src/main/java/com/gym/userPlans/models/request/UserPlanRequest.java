package com.gym.userPlans.models.request;

import com.gym.shared.interfaces.IHandleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPlanRequest implements IHandleRequest {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long planTypeId;
    private String status;
}
