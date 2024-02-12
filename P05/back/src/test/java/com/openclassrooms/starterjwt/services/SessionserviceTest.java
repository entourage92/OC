package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.hibernate.type.LocalDateType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class SessionserviceTest {

    @InjectMocks
    private SessionService sessionservice;

    @Autowired
    private TestEntityManager entityManager;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetbyID_found() {
        Session session = new Session();
        Optional<Session> sessionOptional = Optional.of(session);
        when(sessionRepository.findById(3L)).thenReturn(sessionOptional);
        Session result = sessionservice.getById(3L);
        assertEquals(result, session);
    }

    @Test
    public void testGetbyID_notfound() {
        Optional<Session> sessionOptional = Optional.empty();
        when(sessionRepository.findById(3L)).thenReturn(sessionOptional);
        Session result = sessionservice.getById(3L);
        assertNull(result);
    }

    @Test
    public void testfindAll_empty(){
        List<Session> SessionList = sessionRepository.findAll();
        assertThat(SessionList).isEmpty();
    }

    @Test
    public void testCreate(){
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Mock Session");

        when(sessionRepository.save(Mockito.any(Session.class))).thenReturn(mockSession);
        Session createdSession = sessionservice.create(mockSession);
        verify(sessionRepository).save(mockSession);
        assertEquals(mockSession, createdSession);
    }

    @Test
    public void testParticipate() {
        Long sessionId = 1L;
        Long userId = 2L;

        Session mockSession = new Session();
        mockSession.setId(sessionId);
        mockSession.setUsers(new ArrayList<>());

        User mockUser = new User();
        mockUser.setId(userId);

        when(sessionRepository.findById(sessionId)).thenReturn(java.util.Optional.ofNullable(mockSession));
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.ofNullable(mockUser));

        assertDoesNotThrow(() -> sessionservice.participate(sessionId, userId));

        verify(sessionRepository, times(1)).save(mockSession);

        reset(sessionRepository, userRepository);
    }

    @Test
    public void testalreadyparticipate(){

        Session mockSession = new Session();
        mockSession.setId(1L);
        User mockUser = new User();
        mockUser.setId(1L);
        mockSession.setUsers(Collections.singletonList(mockUser));

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        assertThrows(BadRequestException.class, () -> sessionservice.participate(1L, 1L));
        verify(sessionRepository, Mockito.never()).save(Mockito.any(Session.class));
    }

    @Test
    public void testnolongerparticipate(){

        Session mockSession = new Session();
        mockSession.setId(1L);
        User mockUser = new User();
        mockUser.setId(1L);
        mockSession.setUsers(Collections.singletonList(mockUser));

        when(sessionRepository.findById(mockSession.getId())).thenReturn(Optional.of(mockSession));
        when(sessionRepository.save(any())).thenReturn(mockSession);

        assertDoesNotThrow(() -> sessionservice.noLongerParticipate(mockSession.getId(), mockUser.getId()));

        assertFalse(mockSession.getUsers().stream().anyMatch(u -> u.getId().equals(mockUser.getId())));
        verify(sessionRepository, times(1)).findById(mockSession.getId());
        verify(sessionRepository, times(1)).save(mockSession);
    }

    @Test
    public void testFindAll(){
        Session mockedsession1 = new Session();
        mockedsession1.setId(1L);
        mockedsession1.setName("Session1");

        Session mockedSession2 = new Session();
        mockedSession2.setId(2L);
        mockedSession2.setName("Session2");

        List<Session> mockSessions = Arrays.asList(mockedsession1, mockedSession2);

        when(sessionRepository.save(Mockito.any(Session.class))).thenReturn(mockedsession1, mockedSession2);

        sessionRepository.save(mockedsession1);
        sessionRepository.save(mockedSession2);

        when(sessionRepository.findAll()).thenReturn(mockSessions);

        List<Session> resultSessions = sessionservice.findAll();

        verify(sessionRepository).findAll();
        assertEquals(mockSessions.size(), resultSessions.size());
        assertEquals(mockSessions.get(0), resultSessions.get(0));
        assertEquals(mockSessions.get(1), resultSessions.get(1));
    }

    @Test
    void testDelete() {

        Long sessionId = 1L;

        doNothing().when(sessionRepository).deleteById(sessionId);

        assertDoesNotThrow(() -> sessionservice.delete(sessionId));

        verify(sessionRepository, times(1)).deleteById(sessionId);
    }
}