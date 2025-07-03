package com.gym.serviceImpl;

import com.gym.exceptions.BadRequestException;
import com.gym.exceptions.NotFoundException;
import com.gym.intermediateRelations.models.entity.MachineTimeSlot;
import com.gym.intermediateRelations.repository.MachineTimeSlotRepository;
import com.gym.reservation.models.entity.Attendance;
import com.gym.reservation.models.entity.Reserve;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.AttendanceRequest;
import com.gym.reservation.models.request.ReserveRequest;
import com.gym.reservation.models.request.ReserveSimpleRequest;
import com.gym.reservation.models.response.AttendanceResponse;
import com.gym.reservation.models.response.ReserveResponse;
import com.gym.reservation.repository.AttendanceRepository;
import com.gym.reservation.repository.ReserveRepository;
import com.gym.reservation.repository.TimeSlotRepository;
import com.gym.reservation.service.ReserveServiceImpl;
import com.gym.shared.Constants.ExceptionMessages;
import com.gym.shared.interfaces.MapperConverter;
import com.gym.user.models.entity.User;
import com.gym.user.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class ReserveServiceImplTest {

    @Mock
    private ReserveRepository reserveRepository;
    @Mock
    private MapperConverter<ReserveRequest, ReserveResponse, Reserve> reserveMapper;
    @Mock
    private TimeSlotRepository timeSlotRepository;
    @Mock
    private MachineTimeSlotRepository machineTimeSlotRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private JavaMailSender emailSender;
    @Mock
    private MapperConverter<AttendanceRequest, AttendanceResponse, Attendance> attendanceMapper;

    @InjectMocks
    private ReserveServiceImpl reserveService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindAll() {
        Reserve reserve1 = new Reserve();
        Reserve reserve2 = new Reserve();
        // Inicializa listas para evitar problemas en el mapper
        reserve1.setMachines(List.of());
        reserve1.setTimeSlots(List.of());
        reserve2.setMachines(List.of());
        reserve2.setTimeSlots(List.of());

        ReserveResponse response1 = new ReserveResponse();
        ReserveResponse response2 = new ReserveResponse();

        when(reserveRepository.findAll()).thenReturn(Arrays.asList(reserve1, reserve2));
        when(reserveMapper.mapEntityToDto(reserve1)).thenReturn(response1);
        when(reserveMapper.mapEntityToDto(reserve2)).thenReturn(response2);

        List<ReserveResponse> result = reserveService.findAll();

        assertEquals(2, result.size());
        verify(reserveRepository, times(1)).findAll();
        verify(reserveMapper, times(1)).mapEntityToDto(reserve1);
        verify(reserveMapper, times(1)).mapEntityToDto(reserve2);
    }

    @Test
    void testFindAllByUserId() {
        Long userId = 1L;
        Reserve reserve1 = new Reserve();
        Reserve reserve2 = new Reserve();
        // Inicializa listas y objetos para evitar problemas en el mapper
        reserve1.setMachines(List.of());
        reserve1.setTimeSlots(List.of());
        reserve1.setAttendance(new Attendance());
        reserve2.setMachines(List.of());
        reserve2.setTimeSlots(List.of());
        reserve2.setAttendance(new Attendance());

        ReserveResponse response1 = new ReserveResponse();
        ReserveResponse response2 = new ReserveResponse();

        when(reserveRepository.findAllByUserId(userId)).thenReturn(Arrays.asList(reserve1, reserve2));
        when(reserveMapper.mapEntityToDto(reserve1)).thenReturn(response1);
        when(reserveMapper.mapEntityToDto(reserve2)).thenReturn(response2);

        List<ReserveResponse> result = reserveService.findAllByUserId(userId);

        assertEquals(2, result.size());
        verify(reserveRepository, times(1)).findAllByUserId(userId);
        verify(reserveMapper, times(1)).mapEntityToDto(reserve1);
        verify(reserveMapper, times(1)).mapEntityToDto(reserve2);
    }

    @Test
    void testFindByIdFound() {
        Long reserveId = 1L;
        Reserve reserve = new Reserve();
        // Inicializa listas y objetos para evitar NullPointerException en el mapper
        reserve.setMachines(List.of());
        reserve.setTimeSlots(List.of());
        reserve.setAttendance(new Attendance());

        ReserveResponse response = new ReserveResponse();

        when(reserveRepository.findById(reserveId)).thenReturn(Optional.of(reserve));
        when(reserveMapper.mapEntityToDto(reserve)).thenReturn(response);

        Optional<ReserveResponse> result = reserveService.findById(reserveId);

        assertTrue(result.isPresent());
        assertEquals(response, result.get());
        verify(reserveRepository, times(1)).findById(reserveId);
        verify(reserveMapper, times(1)).mapEntityToDto(reserve);
    }

    @Test
    void testFindByIdNotFound() {
        Long reserveId = 1L;
        when(reserveRepository.findById(reserveId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> reserveService.findById(reserveId));
        assertEquals(ExceptionMessages.RESERVE_NOT_FOUND, exception.getMessage());
        verify(reserveRepository, times(1)).findById(reserveId);
        verifyNoInteractions(reserveMapper);
    }

    @Test
    void testCreateReserveSuccess() throws Exception {
        ReserveRequest request = new ReserveRequest();
        request.setUserId(1L);
        request.setTimeSlotId(List.of(10L, 20L));
        request.setReservationDate(LocalDate.now());
        Map<String, Map<String, Integer>> capacityInfo = Map.of(
                "5", Map.of("10", 1, "20", 1)
        );
        request.setCapacityInfo(capacityInfo);

        // Mocks para TimeSlot y MachineTimeSlot
        TimeSlot timeSlot1 = mock(TimeSlot.class);
        TimeSlot timeSlot2 = mock(TimeSlot.class);
        MachineTimeSlot mts = mock(MachineTimeSlot.class);
        when(timeSlot1.getMachineTimeSlots()).thenReturn(List.of(mts));
        when(timeSlot2.getMachineTimeSlots()).thenReturn(List.of(mts));
        when(mts.getMachine()).thenReturn(mock(com.gym.reservation.models.entity.Machine.class));
        when(mts.getMachine().getId()).thenReturn(5L);
        when(mts.getMachine().getName()).thenReturn("Bicicleta");
        when(mts.getCapacity()).thenReturn(2);

        when(timeSlot1.getStartTime()).thenReturn(java.time.LocalTime.of(8,0));
        when(timeSlot1.getEndTime()).thenReturn(java.time.LocalTime.of(9,0));
        when(timeSlot2.getStartTime()).thenReturn(java.time.LocalTime.of(9,0));
        when(timeSlot2.getEndTime()).thenReturn(java.time.LocalTime.of(10,0));

        when(timeSlotRepository.findById(10L)).thenReturn(Optional.of(timeSlot1));
        when(timeSlotRepository.findById(20L)).thenReturn(Optional.of(timeSlot2));

        // Mock para usuario
        User user = new User();
        user.setIdUser(1L);
        user.setEmail("test@email.com");
        user.setUsername("TestUser");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Mock para Reserve y Attendance
        Reserve reserve = new Reserve();
        reserve.setMachines(List.of(mts.getMachine()));
        reserve.setTimeSlots(List.of(timeSlot1, timeSlot2));
        Attendance attendance = new Attendance();
        reserve.setAttendance(attendance);
        // Inicializa listas para evitar NPE en el flujo real
        reserve.setMachines(List.of(mts.getMachine()));
        reserve.setTimeSlots(List.of(timeSlot1, timeSlot2));

        when(reserveMapper.mapDtoToEntity(request)).thenReturn(reserve);
        when(reserveMapper.mapEntityToDto(reserve)).thenReturn(new ReserveResponse());

        // Mock para email
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        ReserveResponse response = reserveService.create(request);

        assertNotNull(response);
        verify(timeSlotRepository, times(4)).findById(anyLong());
        verify(machineTimeSlotRepository, atLeastOnce()).save(any());
        verify(reserveRepository).save(reserve);
        verify(attendanceRepository).save(attendance);
        verify(emailSender).send(any(MimeMessage.class));
        verify(reserveMapper).mapEntityToDto(reserve);
    }

    @Test
    void testCreateReserveNullRequest() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> reserveService.create(null));
        assertEquals(ExceptionMessages.RESERVE_NOT_FOUND, ex.getMessage());
    }

    @Test
    void testCreateReserveCapacityZero() {
        ReserveRequest request = new ReserveRequest();
        request.setUserId(1L);
        request.setTimeSlotId(List.of(10L));
        request.setReservationDate(LocalDate.now());
        Map<String, Map<String, Integer>> capacityInfo = Map.of(
                "5", Map.of("10", 0)
        );
        request.setCapacityInfo(capacityInfo);

        TimeSlot timeSlot = mock(TimeSlot.class);
        MachineTimeSlot mts = mock(MachineTimeSlot.class);
        when(timeSlot.getMachineTimeSlots()).thenReturn(List.of(mts));
        when(mts.getMachine()).thenReturn(mock(com.gym.reservation.models.entity.Machine.class));
        when(mts.getMachine().getId()).thenReturn(5L);
        when(mts.getMachine().getName()).thenReturn("Bicicleta");
        // Mockear startTime y endTime para evitar NullPointerException
        when(timeSlot.getStartTime()).thenReturn(java.time.LocalTime.of(8, 0));
        when(timeSlot.getEndTime()).thenReturn(java.time.LocalTime.of(9, 0));
        when(timeSlotRepository.findById(10L)).thenReturn(Optional.of(timeSlot));

        BadRequestException ex = assertThrows(BadRequestException.class, () -> reserveService.create(request));
        assertTrue(ex.getMessage().contains("No hay capacidad disponible"));
    }

    @Test
    void testCreateReserveTimeSlotNotFound() {
        ReserveRequest request = new ReserveRequest();
        request.setUserId(1L);
        request.setTimeSlotId(List.of(10L));
        request.setReservationDate(LocalDate.now());
        Map<String, Map<String, Integer>> capacityInfo = Map.of(
                "5", Map.of("10", 1)
        );
        request.setCapacityInfo(capacityInfo);

        when(timeSlotRepository.findById(10L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> reserveService.create(request));
        assertEquals(ExceptionMessages.TIME_SLOT_NOT_FOUND, ex.getMessage());
    }

    @Test
    void testCreateReserveMachineTimeSlotNotFound() {
        ReserveRequest request = new ReserveRequest();
        request.setUserId(1L);
        request.setTimeSlotId(List.of(10L));
        request.setReservationDate(LocalDate.now());
        Map<String, Map<String, Integer>> capacityInfo = Map.of(
                "5", Map.of("10", 1)
        );
        request.setCapacityInfo(capacityInfo);

        TimeSlot timeSlot = mock(TimeSlot.class);
        when(timeSlot.getMachineTimeSlots()).thenReturn(List.of());
        // Mockear startTime y endTime para evitar NullPointerException
        when(timeSlot.getStartTime()).thenReturn(java.time.LocalTime.of(8, 0));
        when(timeSlot.getEndTime()).thenReturn(java.time.LocalTime.of(9, 0));
        when(timeSlotRepository.findById(10L)).thenReturn(Optional.of(timeSlot));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reserveService.create(request));
        assertTrue(ex.getReason().contains("MachineTimeSlot no encontrado"));
    }

    @Test
    void testCreateReserveUserNotFound() {
        ReserveRequest request = new ReserveRequest();
        request.setUserId(1L);
        request.setTimeSlotId(List.of(10L));
        request.setReservationDate(LocalDate.now());
        Map<String, Map<String, Integer>> capacityInfo = Map.of(
                "5", Map.of("10", 1)
        );
        request.setCapacityInfo(capacityInfo);

        TimeSlot timeSlot = mock(TimeSlot.class);
        MachineTimeSlot mts = mock(MachineTimeSlot.class);
        when(timeSlot.getMachineTimeSlots()).thenReturn(List.of(mts));
        when(mts.getMachine()).thenReturn(mock(com.gym.reservation.models.entity.Machine.class));
        when(mts.getMachine().getId()).thenReturn(5L);
        when(mts.getMachine().getName()).thenReturn("Bicicleta");
        when(mts.getCapacity()).thenReturn(2);
        // Mockear startTime y endTime para evitar NullPointerException
        when(timeSlot.getStartTime()).thenReturn(java.time.LocalTime.of(8, 0));
        when(timeSlot.getEndTime()).thenReturn(java.time.LocalTime.of(9, 0));
        when(timeSlotRepository.findById(10L)).thenReturn(Optional.of(timeSlot));

        Reserve reserve = new Reserve();
        Attendance attendance = new Attendance();
        reserve.setAttendance(attendance);
        when(reserveMapper.mapDtoToEntity(request)).thenReturn(reserve);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> reserveService.create(request));
        assertEquals(ExceptionMessages.USER_NOT_FOUND, ex.getMessage());
    }

    @Test
    void testDeleteSuccess() {
        Long reserveId = 1L;
        Reserve reserve = new Reserve();
        when(reserveRepository.findById(reserveId)).thenReturn(Optional.of(reserve));

        String result = reserveService.delete(reserveId);

        assertEquals(ExceptionMessages.RESERVE_DELETED, result);
        verify(reserveRepository).findById(reserveId);
        verify(reserveRepository).delete(reserve);
    }

    @Test
    void testDeleteNotFound() {
        Long reserveId = 1L;
        when(reserveRepository.findById(reserveId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> reserveService.delete(reserveId));
        assertEquals(ExceptionMessages.RESERVE_NOT_FOUND, ex.getMessage());
        verify(reserveRepository).findById(reserveId);
    }

    @Test
    void testGetAllDatesWithReservationsByUserId() {
        Long userId = 1L;
        Reserve reserve1 = new Reserve();
        reserve1.setReservationDate(LocalDate.of(2024, 6, 1));
        Reserve reserve2 = new Reserve();
        reserve2.setReservationDate(LocalDate.of(2024, 6, 2));
        Reserve reserve3 = new Reserve();
        reserve3.setReservationDate(LocalDate.of(2024, 6, 1)); // Fecha repetida

        when(reserveRepository.findAllByUserId(userId)).thenReturn(List.of(reserve1, reserve2, reserve3));

        List<String> result = reserveService.getAllDatesWithReservationsByUserId(userId);

        assertEquals(2, result.size());
        assertTrue(result.contains("01/06/2024"));
        assertTrue(result.contains("02/06/2024"));
        verify(reserveRepository).findAllByUserId(userId);
    }

    @Test
    void testGetAttendanceByUserIdSuccess() {
        Long reserveId = 1L;
        Reserve reserve = new Reserve();
        Attendance attendance = new Attendance();
        reserve.setAttendance(attendance);

        ReserveSimpleRequest request = mock(ReserveSimpleRequest.class);
        AttendanceRequest attendanceRequest = mock(AttendanceRequest.class);
        when(request.getAttendanceRequest()).thenReturn(attendanceRequest);
        when(attendanceRequest.getAttended()).thenReturn(true);
        when(attendanceRequest.getCheckinTime()).thenReturn(LocalDateTime.from(java.time.LocalTime.of(8, 0)));

        when(reserveRepository.findById(reserveId)).thenReturn(Optional.of(reserve));
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        when(attendanceMapper.mapEntityToDto(attendance)).thenReturn(attendanceResponse);

        AttendanceResponse result = reserveService.getAttendanceByUserId(reserveId, request);

        assertEquals(attendanceResponse, result);
        verify(reserveRepository).findById(reserveId);
        verify(attendanceRepository).save(attendance);
        verify(attendanceMapper).mapEntityToDto(attendance);
    }

    @Test
    void testGetAttendanceByUserIdNotFound() {
        Long reserveId = 1L;
        ReserveSimpleRequest request = mock(ReserveSimpleRequest.class);
        when(reserveRepository.findById(reserveId)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> reserveService.getAttendanceByUserId(reserveId, request));
        assertEquals(ExceptionMessages.RESERVE_NOT_FOUND, ex.getMessage());
        verify(reserveRepository).findById(reserveId);
    }
}
