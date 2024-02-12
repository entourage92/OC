package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;

public class TeacherMapperImplTest {

    private final TeacherMapperImpl teacherMapper = new TeacherMapperImpl();

    @Test
    void testToEntity() {

        TeacherDto teacherDto = new TeacherDto(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());

        Teacher teacher = teacherMapper.toEntity(teacherDto);

        assertEquals(teacherDto.getId(), teacher.getId());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
        assertEquals(teacherDto.getCreatedAt(), teacher.getCreatedAt());
        assertEquals(teacherDto.getUpdatedAt(), teacher.getUpdatedAt());
    }

    @Test
    void testToDto() {

        Teacher teacher = new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now());

        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher.getCreatedAt(), teacherDto.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), teacherDto.getUpdatedAt());
    }

    @Test
    void testToEntityList() {

        List<TeacherDto> teacherDtos = Arrays.asList(
                new TeacherDto(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()),
                new TeacherDto(2L, "Smith", "Alice", LocalDateTime.now(), LocalDateTime.now())
        );

        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        assertEquals(teacherDtos.size(), teachers.size());
        for (int i = 0; i < teacherDtos.size(); i++) {
            assertEquals(teacherDtos.get(i).getId(), teachers.get(i).getId());
            assertEquals(teacherDtos.get(i).getLastName(), teachers.get(i).getLastName());
            assertEquals(teacherDtos.get(i).getFirstName(), teachers.get(i).getFirstName());
            assertEquals(teacherDtos.get(i).getCreatedAt(), teachers.get(i).getCreatedAt());
            assertEquals(teacherDtos.get(i).getUpdatedAt(), teachers.get(i).getUpdatedAt());
        }
    }

    @Test
    void testToDtoList() {

        List<Teacher> teachers = Arrays.asList(
                new Teacher(1L, "Doe", "John", LocalDateTime.now(), LocalDateTime.now()),
                new Teacher(2L, "Smith", "Alice", LocalDateTime.now(), LocalDateTime.now())
        );

        List<TeacherDto> teacherDtos = teacherMapper.toDto(teachers);

        assertEquals(teachers.size(), teacherDtos.size());
        for (int i = 0; i < teachers.size(); i++) {
            assertEquals(teachers.get(i).getId(), teacherDtos.get(i).getId());
            assertEquals(teachers.get(i).getLastName(), teacherDtos.get(i).getLastName());
            assertEquals(teachers.get(i).getFirstName(), teacherDtos.get(i).getFirstName());
            assertEquals(teachers.get(i).getCreatedAt(), teacherDtos.get(i).getCreatedAt());
            assertEquals(teachers.get(i).getUpdatedAt(), teacherDtos.get(i).getUpdatedAt());
        }
    }
}