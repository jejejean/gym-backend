package com.gym.userPlans.models.mapper;

import com.gym.shared.interfaces.MapperConverter;
import com.gym.userPlans.models.entity.PlanType;
import com.gym.userPlans.models.request.PlanTypeRequest;
import com.gym.userPlans.models.response.PlanTypeResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanTypeMapper implements MapperConverter<PlanTypeRequest, PlanTypeResponse, PlanType> {
    private final ModelMapper modelMapper;

    @Override
    public PlanTypeResponse mapEntityToDto(PlanType entity) {
        return modelMapper.map(entity, PlanTypeResponse.class);
    }

    @Override
    public PlanType mapDtoToEntity(PlanTypeRequest request) {
        return modelMapper.map(request, PlanType.class);
    }
}
