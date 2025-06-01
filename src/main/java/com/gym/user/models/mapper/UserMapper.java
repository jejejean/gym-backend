package com.gym.user.models.mapper;

import com.gym.exceptions.NotFoundException;
import com.gym.shared.Constants.ExceptionMessages;
import com.gym.shared.interfaces.MapperConverter;
import com.gym.user.interfaces.UserMapEntityToDto;
import com.gym.user.models.entity.Role;
import com.gym.user.models.entity.User;
import com.gym.user.models.entity.UserProfile;
import com.gym.user.models.request.UserProfileRequest;
import com.gym.user.models.request.UserRequest;
import com.gym.user.models.response.UserProfileResponse;
import com.gym.user.models.response.UserResponse;
import com.gym.user.repository.RoleRepository;
import com.gym.userPlans.models.entity.UserPlan;
import com.gym.userPlans.models.request.UserPlanRequest;
import com.gym.userPlans.models.response.UserPlanResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserMapper implements UserMapEntityToDto, MapperConverter<UserRequest, UserResponse, User> {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final MapperConverter<UserProfileRequest, UserProfileResponse, UserProfile> userProfileMapper;
    private final MapperConverter<UserPlanRequest, UserPlanResponse, UserPlan> userPlanMapper;

    @Override
    public UserResponse entityToDto(User user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        // Mapea los roles a una lista de strings
        userResponse.setRoles(user.getRoles().toString());
        return userResponse;
    }


    @Override
    public UserResponse mapEntityToDto(User entity) {
        UserResponse userResponse = modelMapper.map(entity, UserResponse.class);
        UserProfileResponse userProfile = userProfileMapper.mapEntityToDto(entity.getUserProfile());
        List<UserPlanResponse> userPlanResponses = entity.getUserPlans().stream()
                .map(userPlanMapper::mapEntityToDto)
                .toList();

        userResponse.setUserProfileResponse(userProfile);
        userResponse.setUserPlansResponse(userPlanResponses);
        return userResponse;
    }

    @Override
    public User mapDtoToEntity(UserRequest request) {
        User user = modelMapper.map(request, User.class);
        UserProfile userProfile = userProfileMapper.mapDtoToEntity(request.getUserProfileRequest());
        List<UserPlan> userPlans = request.getUserPlansRequest().stream()
                        .map(userPlanMapper::mapDtoToEntity)
                        .toList();

        Role role = roleRepository.findByRol(user.getUserType())
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.ROLE_NOT_FOUND));

        user.setIdUser(null);
        user.setStatus("Activo");
        user.setUserType(user.getUserType().equals("Cliente") ? "Cliente" : "Interno");
        String firstName = request.getUsername().split(" ")[0];
        user.setPassword(passwordEncoder.encode(firstName + user.getPhone()));
        user.setRoles(Collections.singletonList(role));
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        userPlans.forEach(plan -> plan.setUser(user));
        user.setUserPlans(userPlans);
        return user;
    }
}
