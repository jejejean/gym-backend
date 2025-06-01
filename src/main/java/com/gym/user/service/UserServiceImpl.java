package com.gym.user.service;

import com.gym.exceptions.NotFoundException;
import com.gym.shared.Constants.ExceptionMessages;
import com.gym.shared.interfaces.CrudInterface;
import com.gym.shared.interfaces.MapperConverter;
import com.gym.user.interfaces.UserService;
import com.gym.user.models.entity.User;
import com.gym.user.models.entity.UserProfile;
import com.gym.user.models.mapper.UserMapper;
import com.gym.user.models.request.UserRequest;
import com.gym.user.models.response.UserResponse;
import com.gym.user.repository.UserProfileRepository;
import com.gym.user.repository.UserRepository;
import com.gym.userPlans.models.entity.PlanType;
import com.gym.userPlans.models.entity.UserPlan;
import com.gym.userPlans.repository.PlanTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements CrudInterface<UserRequest, UserResponse>, UserService {

    private final UserRepository userRepository;
    private final MapperConverter<UserRequest, UserResponse, User> userMapper;
    private final UserProfileRepository userProfileRepository;
    private final PlanTypeRepository planTypeRepository;

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public List<UserResponse> findAllByUserType() {
        List<User> users = userRepository.findAllByUserType();
        return users.stream()
                .map(userMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public Optional<UserResponse> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }
        return Optional.of(userMapper.mapEntityToDto(user.get()));
    }

    @Override
    public UserResponse create(UserRequest request) {
        if (request == null) {
            throw new NotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }
        User user = userMapper.mapDtoToEntity(request);
        userRepository.save(user);
        return userMapper.mapEntityToDto(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));

        UserProfile userProfile = userProfileRepository.findByUserIdUser(request.getIdUser());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        if(userProfile != null) {
            userProfile.setWeight(request.getUserProfileRequest().getWeight());
            userProfile.setHeight(request.getUserProfileRequest().getHeight());
            userProfile.setSex(request.getUserProfileRequest().getSex());
        }
        user.setUserProfile(userProfile);
        userRepository.save(user);
        return userMapper.mapEntityToDto(user);
    }
    @Override
    public UserResponse updateUserPlan(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));

        if (user.getStatus().equals("Activo") && request.getUserPlansRequest() != null) {
            // Mapear y actualizar los planes del usuario
            List<UserPlan> userPlans = request.getUserPlansRequest().stream()
                    .map(userPlanRequest -> {
                        PlanType planType = planTypeRepository.findById(userPlanRequest.getPlanTypeId())
                                .orElseThrow(() -> new NotFoundException(ExceptionMessages.PLAN_TYPE_NOT_FOUND));

                        UserPlan userPlan = new UserPlan();
                        userPlan.setStartDate(userPlanRequest.getStartDate());
                        userPlan.setEndDate(userPlanRequest.getEndDate());
                        userPlan.setStatus("RENOVADO");
                        userPlan.setPlanType(planType);
                        userPlan.setUser(user);
                        return userPlan;
                    })
                    .toList();

            // Asignar los nuevos planes al usuario
            user.setUserPlans(userPlans);
        }

        userRepository.save(user);
        return userMapper.mapEntityToDto(user);
    }

    @Override
    public String delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));
        userRepository.delete(user);
        return ExceptionMessages.USER_DELETED;
    }
}
