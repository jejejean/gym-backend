package com.gym.reservation.service;

import com.gym.exceptions.NotFoundException;
import com.gym.reservation.interfaces.ReserveService;
import com.gym.reservation.models.entity.Attendance;
import com.gym.reservation.models.entity.Machine;
import com.gym.reservation.models.entity.Reserve;
import com.gym.reservation.models.entity.TimeSlot;
import com.gym.reservation.models.request.AttendanceRequest;
import com.gym.reservation.models.request.ReserveRequest;
import com.gym.reservation.models.request.ReserveSimpleRequest;
import com.gym.reservation.models.response.AttendanceResponse;
import com.gym.reservation.models.response.ReserveByDayResponse;
import com.gym.reservation.models.response.ReserveResponse;
import com.gym.reservation.models.response.TimeSlotResponse;
import com.gym.reservation.repository.AttendanceRepository;
import com.gym.reservation.repository.ReserveRepository;
import com.gym.reservation.repository.TimeSlotRepository;
import com.gym.shared.Constants.ExceptionMessages;
import com.gym.shared.interfaces.CrudInterface;
import com.gym.shared.interfaces.MapperConverter;
import com.gym.user.models.entity.User;
import com.gym.user.models.response.UserProfileResponse;
import com.gym.user.models.response.UserSimpleResponse;
import com.gym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReserveServiceImpl implements CrudInterface<ReserveRequest, ReserveResponse>, ReserveService {

    private final ReserveRepository reserveRepository;
    private final MapperConverter<ReserveRequest, ReserveResponse, Reserve> reserveMapper;
    private final MapperConverter<AttendanceRequest, AttendanceResponse, Attendance> attendanceMapper;
    private final TimeSlotRepository timeSlotRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    @Override
    public List<ReserveResponse> findAll() {
        List<Reserve> reserves = reserveRepository.findAll();
        return reserves.stream()
                .map(reserveMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public List<ReserveResponse> findAllByUserId(Long userId) {
        List<Reserve> reserves = reserveRepository.findAllByUserId(userId);
        return reserves.stream()
                .map(reserveMapper::mapEntityToDto)
                .toList();
    }

    @Override
    public Optional<ReserveResponse> findById(Long id) {
        Optional<Reserve> reserve = reserveRepository.findById(id);
        if (reserve.isEmpty()) {
            throw new NotFoundException(ExceptionMessages.RESERVE_NOT_FOUND);
        }
        return Optional.of(reserveMapper.mapEntityToDto(reserve.get()));
    }

    @Override
    public ReserveResponse create(ReserveRequest request) {
        if (request == null) {
            throw new NotFoundException(ExceptionMessages.RESERVE_NOT_FOUND);
        }
        Reserve reserve = reserveMapper.mapDtoToEntity(request);
        reserveRepository.save(reserve);

        Attendance attendance = reserve.getAttendance();
        attendance.setReserve(reserve);
        attendanceRepository.save(attendance);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));

        TimeSlot firstTimeSlot = timeSlotRepository.findById(request.getTimeSlotId().get(0))
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.TIME_SLOT_NOT_FOUND));

        int lastIndex = request.getTimeSlotId().size() - 1;
        TimeSlot lastTimeSlot = timeSlotRepository.findById(request.getTimeSlotId().get(lastIndex))
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.TIME_SLOT_NOT_FOUND));

        String maquinas = reserve.getMachines().isEmpty()
                ? "Ninguna"
                : reserve.getMachines().stream()
                .map(machine -> "\t- " + machine.getName())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("Ninguna");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        SimpleMailMessage message = new SimpleMailMessage();
        String fechaFormateada = request.getReservationDate().format(formatter);

        message.setTo(user.getEmail());
        message.setSubject("Confirmación de Reserva - VitalFit");
        message.setText("¡Hola " + user.getUsername() + "!\n\n" +
                "Se ha registrado una reserva:\n" +
                "Dia: " + fechaFormateada + "\n" +
                "Hora: " + firstTimeSlot.getStartTime() + " - " + lastTimeSlot.getEndTime() +" \n" +
                "Máquinas reservadas:\n" + maquinas + "\n\n" +
                "Gracias por preferirnos.");

        emailSender.send(message);
        //scheduleReminderEmail(user, request.getReservationDate(), lastTimeSlot.getStartTime());
        return reserveMapper.mapEntityToDto(reserve);
    }

    @Override
    public ReserveResponse update(Long id, ReserveRequest request) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.RESERVE_NOT_FOUND));

        List<TimeSlot> timeSlots = new ArrayList<>();
        for (Long timeSlotId : request.getTimeSlotId()) {
            TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                    .orElseThrow(() -> new NotFoundException(ExceptionMessages.TIME_SLOT_NOT_FOUND));
            timeSlots.add(timeSlot);
        }
        reserve.getTimeSlots().clear();
        reserve.getTimeSlots().addAll(timeSlots);
        //reserve.setDetails(request.getDetails());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SimpleMailMessage message = new SimpleMailMessage();
        String fechaFormateada = request.getReservationDate().format(formatter);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));

        TimeSlot firstTimeSlot = timeSlotRepository.findById(request.getTimeSlotId().get(0))
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.TIME_SLOT_NOT_FOUND));

        int lastIndex = request.getTimeSlotId().size() - 1;
        TimeSlot lastTimeSlot = timeSlotRepository.findById(request.getTimeSlotId().get(lastIndex))
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.TIME_SLOT_NOT_FOUND));

        message.setTo(user.getEmail());
        message.setSubject("Confirmación de Reserva - VitalFit");
        message.setText("¡Hola " + user.getUsername() + "!\n\n" +
                "Se ha actualizado la reserva:\n" +
                "Dia: " + fechaFormateada + "\n" +
                "Hora: " + firstTimeSlot.getStartTime() + " - " + lastTimeSlot.getEndTime() +" \n" +
                "Gracias por preferirnos.");

        emailSender.send(message);
        reserveRepository.save(reserve);
        return reserveMapper.mapEntityToDto(reserve);
    }

    @Override
    public String delete(Long id) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.RESERVE_NOT_FOUND));
        reserveRepository.delete(reserve);
        return ExceptionMessages.RESERVE_DELETED;
    }

    @Override
    public List<String> getAllDatesWithReservationsByUserId(Long userId) {
        List<Reserve> reserves = reserveRepository.findAllByUserId(userId);
        return reserves.stream()
                .map(reserve -> reserve.getReservationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .distinct()
                .toList();
    }

    @Override
    public AttendanceResponse getAttendanceByUserId(Long id, ReserveSimpleRequest request) {
        Reserve reserve = reserveRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessages.RESERVE_NOT_FOUND));

        Attendance attendance = reserve.getAttendance();
        attendance.setAttended(request.getAttendanceRequest().getAttended());
        attendance.setCheckinTime(request.getAttendanceRequest().getCheckinTime());
        attendanceRepository.save(attendance);
        return attendanceMapper.mapEntityToDto(attendance);
    }

    @Override
    public List<ReserveByDayResponse> findAllByReservationDate(LocalDate reservationDate) {
        List<Reserve> reserves = reserveRepository.findAllByReservationDate(reservationDate);
        if (reserves.isEmpty()) {
            throw new NotFoundException(ExceptionMessages.RESERVE_NOT_FOUND);
        }
        return reserves.stream()
                .map(reserve -> {
                    ReserveByDayResponse response = new ReserveByDayResponse();
                    response.setId(reserve.getId());
                    //response.setMachine(reserve.getMachine());
                    response.setReservationDate(reserve.getReservationDate());

                    User user = userRepository.findById(reserve.getUser().getIdUser())
                            .orElseThrow(() -> new NotFoundException(ExceptionMessages.USER_NOT_FOUND));
                    if (user != null) {
                        //user user = reserve.getUser();
                        UserSimpleResponse userSimpleResponse = new UserSimpleResponse();
                        userSimpleResponse.setIdUser(user.getIdUser());
                        userSimpleResponse.setUsername(user.getUsername());
                        userSimpleResponse.setEmail(user.getEmail());
                        userSimpleResponse.setPhone(user.getPhone());
                        userSimpleResponse.setStatus(user.getStatus());

                        UserProfileResponse profileResponse = new UserProfileResponse();
                        profileResponse.setId(user.getUserProfile().getId());
                        profileResponse.setSex(user.getUserProfile().getSex());
                        profileResponse.setHeight(user.getUserProfile().getHeight());
                        profileResponse.setWeight(user.getUserProfile().getWeight());
                        userSimpleResponse.setUserProfileResponse(profileResponse);
                        response.setUserSimpleResponse(userSimpleResponse);

                    }
                    Attendance attendance = attendanceRepository.findById(reserve.getAttendance().getId())
                            .orElseThrow(() -> new NotFoundException(ExceptionMessages.ATTENDANCE_NOT_FOUND));

                    if (attendance != null) {
                        //Attendance attendance = reserve.getAttendance();
                        AttendanceResponse attendanceResponse = new AttendanceResponse();
                        attendanceResponse.setId(attendance.getId());
                        attendanceResponse.setAttended(attendance.getAttended());
                        attendanceResponse.setCheckinTime(attendance.getCheckinTime());
                        response.setAttendanceResponse(attendanceResponse);
                    }

                    List<TimeSlotResponse> timeSlotResponses = reserve.getTimeSlots().stream()
                            .map(timeSlot -> {
                                TimeSlotResponse timeSlotResponse = new TimeSlotResponse();
                                timeSlotResponse.setId(timeSlot.getId());
                                timeSlotResponse.setStartTime(timeSlot.getStartTime());
                                timeSlotResponse.setEndTime(timeSlot.getEndTime());
                                return timeSlotResponse;
                            })
                            .toList();
                    response.setTimeSlotResponse(timeSlotResponses);

                    return response;
                })
                .toList();
    }

    /*
    private void scheduleReminderEmail(User user, LocalDate reservationDate, LocalTime startTime) {
        ZoneId peruZone = ZoneId.of("America/Lima");
        LocalDateTime startDateTime = LocalDateTime.of(reservationDate, startTime);
        LocalDateTime reminderTime = startDateTime.minusMinutes(30);
        LocalDateTime now = LocalDateTime.now(peruZone);

        if (reminderTime.isAfter(now)) {
            // Programar recordatorio para 30 minutos antes
            TaskScheduler taskScheduler = new ConcurrentTaskScheduler();
            taskScheduler.schedule(() -> {
                SimpleMailMessage reminderMessage = new SimpleMailMessage();
                reminderMessage.setTo(user.getEmail());
                reminderMessage.setSubject("Recordatorio de Reserva - VitalFit");
                reminderMessage.setText("¡Hola " + user.getUsername() + "!\n\n" +
                        "Faltan 30 min para asistir a tu reserva en el gym");
                emailSender.send(reminderMessage);
            }, Date.from(reminderTime.atZone(peruZone).toInstant()));
        } else if (startDateTime.isAfter(now)) {
            // Enviar recordatorio inmediato si la reserva aún no ha comenzado
            SimpleMailMessage reminderMessage = new SimpleMailMessage();
            reminderMessage.setTo(user.getEmail());
            reminderMessage.setSubject("Recordatorio de Reserva - VitalFit");
            reminderMessage.setText("¡Hola " + user.getUsername() + "!\n\n" +
                    "Tu reserva en el gym comenzará pronto. ¡Te esperamos!");
            emailSender.send(reminderMessage);
            System.out.println("Enviando recordatorio inmediato para reserva próxima");
        } else {
            System.out.println("La reserva ya comenzó, no se enviará recordatorio");
        }
    }
     */
}
