package com.gym.userPlans.models.mapper;

import com.gym.exceptions.NotFoundException;
import com.gym.shared.Constants.ExceptionMessages;
import com.gym.shared.interfaces.MapperConverter;
import com.gym.userPlans.models.entity.PlanType;
import com.gym.userPlans.models.entity.UserPlan;
import com.gym.userPlans.models.request.PlanTypeRequest;
import com.gym.userPlans.models.request.UserPlanRequest;
import com.gym.userPlans.models.response.PlanTypeResponse;
import com.gym.userPlans.models.response.UserPlanResponse;
import com.gym.userPlans.repository.PlanTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPlanMapper implements MapperConverter<UserPlanRequest, UserPlanResponse, UserPlan> {
    private final ModelMapper modelMapper;
    private final PlanTypeRepository planTypeRepository;
    private final MapperConverter<PlanTypeRequest, PlanTypeResponse, PlanType> planTypeMapper;

    @Override
    public UserPlanResponse mapEntityToDto(UserPlan entity) {
        UserPlanResponse userPlanResponse = modelMapper.map(entity, UserPlanResponse.class);
        PlanTypeResponse planTypeResponse = planTypeMapper.mapEntityToDto(entity.getPlanType());
        userPlanResponse.setPlanTypeResponse(planTypeResponse);
        return userPlanResponse;
    }

    @Override
    public UserPlan mapDtoToEntity(UserPlanRequest request) {
        UserPlan userPlan = modelMapper.map(request, UserPlan.class);

        PlanType planType = planTypeRepository.findById(request.getPlanTypeId())
                        .orElseThrow(() -> new NotFoundException(ExceptionMessages.PLAN_TYPE_NOT_FOUND));

        userPlan.setId(null);
        userPlan.setStatus("Activo");
        userPlan.setPlanType(planType);
        return userPlan;
    }
}
