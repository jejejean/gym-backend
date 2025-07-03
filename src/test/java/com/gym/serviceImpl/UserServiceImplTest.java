package com.gym.serviceImpl;

import com.gym.exceptions.NotFoundException;
import com.gym.shared.Constants.ExceptionMessages;
import com.gym.shared.interfaces.MapperConverter;
import com.gym.user.models.entity.User;
import com.gym.user.models.entity.UserProfile;
import com.gym.user.models.request.UserRequest;
import com.gym.user.models.response.UserResponse;
import com.gym.user.repository.UserProfileRepository;
import com.gym.user.repository.UserRepository;
import com.gym.user.service.UserServiceImpl;
import com.gym.userPlans.repository.PlanTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class UserImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private MapperConverter userMapper;
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private PlanTypeRepository planTypeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindAll() {
        User user1 = new User();
        User user2 = new User();
        UserResponse response1 = new UserResponse();
        UserResponse response2 = new UserResponse();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.mapEntityToDto(user1)).thenReturn(response1);
        when(userMapper.mapEntityToDto(user2)).thenReturn(response2);

        List<UserResponse> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).mapEntityToDto(user1);
        verify(userMapper, times(1)).mapEntityToDto(user2);
    }

    @Test
    void testFindAllByUserType() {
        User user1 = new User();
        User user2 = new User();
        UserResponse response1 = new UserResponse();
        UserResponse response2 = new UserResponse();

        when(userRepository.findAllByUserType()).thenReturn(Arrays.asList(user1, user2));
        when(userMapper.mapEntityToDto(user1)).thenReturn(response1);
        when(userMapper.mapEntityToDto(user2)).thenReturn(response2);

        List<UserResponse> result = userService.findAllByUserType();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAllByUserType();
        verify(userMapper, times(1)).mapEntityToDto(user1);
        verify(userMapper, times(1)).mapEntityToDto(user2);
    }

    @Test
    void testFindByIdFound() {
        User user = new User();
        UserResponse response = new UserResponse();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.mapEntityToDto(user)).thenReturn(response);

        Optional<UserResponse> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).mapEntityToDto(user);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.findById(1L));
        assertEquals(ExceptionMessages.USER_NOT_FOUND, exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(userMapper);
    }

    @Test
    void testCreateUserSuccess() {
        UserRequest request = new UserRequest();
        request.setEmail("test@email.com");
        User user = new User();
        UserResponse response = new UserResponse();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userMapper.mapDtoToEntity(request)).thenReturn(user);
        when(userMapper.mapEntityToDto(user)).thenReturn(response);

        UserResponse result = userService.create(request);

        assertEquals(response, result);
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).mapDtoToEntity(request);
        verify(userMapper, times(1)).mapEntityToDto(user);
    }

    @Test
    void testCreateUserNullRequest() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.create(null));
        assertEquals(ExceptionMessages.USER_NOT_FOUND, exception.getMessage());
        verifyNoInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    void testCreateUserEmailExists() {
        UserRequest request = new UserRequest();
        request.setEmail("test@email.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.create(request));
        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(request.getEmail());
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    void testUpdateUserSuccess() {
        Long userId = 1L;
        UserRequest request = new UserRequest();
        request.setIdUser(userId);
        request.setUsername("nuevoNombre");
        request.setPhone("123456789");
        request.setEmail("nuevo@email.com");

        // Mock de UserProfileRequest
        var userProfileRequest = new com.gym.user.models.request.UserProfileRequest();
        userProfileRequest.setWeight(70.0);
        userProfileRequest.setHeight(1.75);
        userProfileRequest.setSex("M");
        request.setUserProfileRequest(userProfileRequest);

        User user = new User();
        user.setIdUser(userId);

        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);

        UserResponse response = new UserResponse();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userProfileRepository.findByUserIdUser(userId)).thenReturn(userProfile);
        when(userMapper.mapEntityToDto(user)).thenReturn(response);

        UserResponse result = userService.update(userId, request);

        assertEquals(response, result);
        assertEquals("nuevoNombre", user.getUsername());
        assertEquals("123456789", user.getPhone());
        assertEquals("nuevo@email.com", user.getEmail());
        assertEquals(70.0, userProfile.getWeight());
        assertEquals(1.75, userProfile.getHeight());
        assertEquals("M", userProfile.getSex());

        verify(userRepository, times(1)).findById(userId);
        verify(userProfileRepository, times(1)).findByUserIdUser(userId);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).mapEntityToDto(user);
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        UserRequest request = new UserRequest();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.update(userId, request));
        assertEquals(ExceptionMessages.USER_NOT_FOUND, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userProfileRepository);
        verifyNoInteractions(userMapper);
    }

}
