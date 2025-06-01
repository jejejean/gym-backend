package com.gym.user.models.mapper;

import com.gym.shared.interfaces.MapperConverter;
import com.gym.user.models.entity.UserProfile;
import com.gym.user.models.request.UserProfileRequest;
import com.gym.user.models.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileMapper implements MapperConverter<UserProfileRequest, UserProfileResponse, UserProfile> {

    private final ModelMapper modelMapper;


    @Override
    public UserProfileResponse mapEntityToDto(UserProfile entity) {
        UserProfileResponse userProfileResponse = modelMapper.map(entity, UserProfileResponse.class);
        return userProfileResponse;
    }

    @Override
    public UserProfile mapDtoToEntity(UserProfileRequest request) {
        UserProfile userProfile = modelMapper.map(request, UserProfile.class);
        userProfile.setId(null);
        return userProfile;
    }
}
