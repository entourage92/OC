package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToDto() {

        User user = new User(1L, "test@example.com", "Doe", "John", "password", false, null, null);

        UserDto userDto = userMapper.toDto(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.isAdmin(), userDto.isAdmin());

    }

    @Test
    void testToEntity() {

        UserDto userDto = new UserDto(1L, "test@example.com", "Doe", "John", false, "password", null, null);

        User user = userMapper.toEntity(userDto);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.isAdmin(), user.isAdmin());

    }

    @Test
    void testToEntityList() {

        UserDto userDto1 = new UserDto(1L, "user1@example.com", "User1LastName", "User1FirstName", false, "password", null, null);
        UserDto userDto2 = new UserDto(2L, "user2@example.com", "User2LastName", "User2FirstName", true, "password", null, null);

        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2);

        List<User> userList = userMapper.toEntity(userDtoList);

        assertEquals(userDtoList.size(), userList.size());

        User firstUser = userList.get(0);
        assertEquals(userDto1.getId(), firstUser.getId());
        assertEquals(userDto1.getEmail(), firstUser.getEmail());
        assertEquals(userDto1.getLastName(), firstUser.getLastName());
        assertEquals(userDto1.getFirstName(), firstUser.getFirstName());
        assertEquals(userDto1.isAdmin(), firstUser.isAdmin());
        assertEquals(userDto1.getPassword(), firstUser.getPassword());
        assertNull(firstUser.getCreatedAt());
        assertNull(firstUser.getUpdatedAt());

        User secondUser = userList.get(1);
        assertEquals(userDto2.getId(), secondUser.getId());
        assertEquals(userDto2.getEmail(), secondUser.getEmail());
        assertEquals(userDto2.getLastName(), secondUser.getLastName());
        assertEquals(userDto2.getFirstName(), secondUser.getFirstName());
        assertEquals(userDto2.isAdmin(), secondUser.isAdmin());
        assertEquals(userDto2.getPassword(), secondUser.getPassword());
        assertNull(secondUser.getCreatedAt());
        assertNull(secondUser.getUpdatedAt());

        assertEquals(Collections.emptyList(), userMapper.toEntity(Collections.emptyList()));
    }

    @Test
    void testToDtoList() {

        User user1 = new User(1L, "user1@example.com", "User1LastName", "User1FirstName", "password", false, null, null);
        User user2 = new User(2L, "user2@example.com", "User2LastName", "User2FirstName", "password", true, null, null);

        List<User> userList = Arrays.asList(user1, user2);

        List<UserDto> userDtoList = userMapper.toDto(userList);

        assertEquals(userList.size(), userDtoList.size());

        UserDto firstUserDto = userDtoList.get(0);
        assertEquals(user1.getId(), firstUserDto.getId());
        assertEquals(user1.getEmail(), firstUserDto.getEmail());
        assertEquals(user1.getLastName(), firstUserDto.getLastName());
        assertEquals(user1.getFirstName(), firstUserDto.getFirstName());
        assertEquals(user1.isAdmin(), firstUserDto.isAdmin());
        assertNull(firstUserDto.getCreatedAt());
        assertNull(firstUserDto.getUpdatedAt());

        UserDto secondUserDto = userDtoList.get(1);
        assertEquals(user2.getId(), secondUserDto.getId());
        assertEquals(user2.getEmail(), secondUserDto.getEmail());
        assertEquals(user2.getLastName(), secondUserDto.getLastName());
        assertEquals(user2.getFirstName(), secondUserDto.getFirstName());
        assertEquals(user2.isAdmin(), secondUserDto.isAdmin());
        assertNull(secondUserDto.getCreatedAt());
        assertNull(secondUserDto.getUpdatedAt());

        assertEquals(Collections.emptyList(), userMapper.toDto(Collections.emptyList()));
    }
}