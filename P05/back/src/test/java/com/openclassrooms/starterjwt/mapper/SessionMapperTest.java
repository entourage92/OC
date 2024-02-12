package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    public SessionMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntityList() {

        Teacher teacher = new Teacher(1L, "TeacherLastName", "TeacherFirstName", null, null);
        User user1 = new User(1L, "user1@example.com", "User1LastName", "User1FirstName", "password", false, null, null);
        User user2 = new User(2L, "user2@example.com", "User2LastName", "User2FirstName", "password", false, null, null);

        List<User> users = Arrays.asList(user1, user2);
        List<SessionDto> sessionDtoList = Arrays.asList(
                new SessionDto(1L, "Session 1", new Date(), 1L, "Description 1", Arrays.asList(user1.getId(), user2.getId()), null, null),
                new SessionDto(2L, "Session 2", new Date(), 2L, "Description 2", Arrays.asList(user1.getId(), user2.getId()), null, null)
        );

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherService.findById(2L)).thenReturn(null);

        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        List<Session> sessionList = sessionMapper.toEntity(sessionDtoList);

        assertEquals(sessionDtoList.size(), sessionList.size());

        Session firstSession = sessionList.get(0);
        assertEquals(sessionDtoList.get(0).getId(), firstSession.getId());
        assertEquals(sessionDtoList.get(0).getName(), firstSession.getName());
        assertEquals(sessionDtoList.get(0).getDate(), firstSession.getDate());
        assertEquals(sessionDtoList.get(0).getDescription(), firstSession.getDescription());
        assertEquals(teacher, firstSession.getTeacher());
        assertEquals(users, firstSession.getUsers());

        Session secondSession = sessionList.get(1);
        assertEquals(sessionDtoList.get(1).getId(), secondSession.getId());
        assertEquals(sessionDtoList.get(1).getName(), secondSession.getName());
        assertEquals(sessionDtoList.get(1).getDate(), secondSession.getDate());
        assertEquals(sessionDtoList.get(1).getDescription(), secondSession.getDescription());
        assertEquals(null, secondSession.getTeacher());
        assertEquals(users, secondSession.getUsers());
    }

    @Test
    void testToDto() {

        Teacher teacher = new Teacher(1L, "TeacherLastName", "TeacherFirstName", null, null);
        User user1 = new User(1L, "user1@example.com", "User1LastName", "User1FirstName", "password", false, null, null);
        User user2 = new User(2L, "user2@example.com", "User2LastName", "User2FirstName", "password", false, null, null);

        List<User> users = Arrays.asList(user1, user2);
        Session session = new Session(1L, "Session 1", new Date(), "Description 1", teacher, users, null, null);

        when(teacherService.findById(1L)).thenReturn(teacher);

        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDate(), sessionDto.getDate());
        assertEquals(session.getDescription(), sessionDto.getDescription());
        assertEquals(session.getTeacher().getId(), sessionDto.getTeacher_id());
        assertEquals(Arrays.asList(user1.getId(), user2.getId()), sessionDto.getUsers());

    }

    @Test
    void testToEntity() {

        Teacher teacher = new Teacher(1L, "TeacherLastName", "TeacherFirstName", null, null);
        User user1 = new User(1L, "user1@example.com", "User1LastName", "User1FirstName", "password", false, null, null);
        User user2 = new User(2L, "user2@example.com", "User2LastName", "User2FirstName", "password", false, null, null);

        List<Long> userIds = Arrays.asList(user1.getId(), user2.getId());

        SessionDto sessionDto = new SessionDto(1L, "Session 1", new Date(), 1L, "Description 1", userIds, null, null);

        when(teacherService.findById(1L)).thenReturn(teacher);

        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        Session session = sessionMapper.toEntity(sessionDto);

        assertEquals(sessionDto.getId(), session.getId());
        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getDate(), session.getDate());
        assertEquals(sessionDto.getDescription(), session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(Arrays.asList(user1, user2), session.getUsers());

    }

    @Test
    void testToDtoWithNullValues() {

        Session session = new Session();

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDate(), sessionDto.getDate());
        assertEquals(session.getDescription(), sessionDto.getDescription());
        assertEquals(null, sessionDto.getTeacher_id());
        assertEquals(Collections.emptyList(), sessionDto.getUsers());

    }
}