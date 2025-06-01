package com.gym.userPlans.models.response;

import com.gym.shared.interfaces.IHandleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPlanResponse implements IHandleResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private PlanTypeResponse planTypeResponse;
}
