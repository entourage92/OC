package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.mapper.TeacherMapperImpl;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherService teacherService;

    private final TeacherMapper teacherMapper = new TeacherMapperImpl();

    @Test
    public void testFindAll(){
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("Teacher1");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Teacher2");

        List<Teacher> mockTeachers = Arrays.asList(teacher1, teacher2);

        when(teacherRepository.save(Mockito.any(Teacher.class))).thenReturn(teacher1, teacher2);

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        when(teacherRepository.findAll()).thenReturn(mockTeachers);

        List<Teacher> resultTeachers = teacherService.findAll();

        verify(teacherRepository).findAll();
        assertEquals(mockTeachers.size(), resultTeachers.size());
        assertEquals(mockTeachers.get(0), resultTeachers.get(0));
        assertEquals(mockTeachers.get(1), resultTeachers.get(1));
    }

    @Test
    public void testGetbyID_found() {
        Teacher mockteacher = new Teacher();
        mockteacher.setId(1L);
        mockteacher.setFirstName("Mock Teacher");

        when(teacherRepository.save(Mockito.any(Teacher.class))).thenReturn(mockteacher);
        Teacher createdteacher = teacherRepository.save(mockteacher);
        verify(teacherRepository).save(mockteacher);
        assertEquals(mockteacher, createdteacher);
    }

    @Test
    public void testGetbyID_notfound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        Teacher resultTeacher = teacherService.findById(1L);
        verify(teacherRepository).findById(1L);
        assertNull(resultTeacher);
    }
}
