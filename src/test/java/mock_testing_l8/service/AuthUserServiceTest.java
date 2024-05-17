package mock_testing_l8.service;

import mock_testing_l8.dto.AuthUserCreateDto;
import mock_testing_l8.dto.AuthUserGetDto;
import mock_testing_l8.dto.AuthUserUpdateDto;
import mock_testing_l8.entity.AuthUser;
import mock_testing_l8.repository.AuthUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserServiceTest {
    @Mock
    AuthUserRepository repository;

    @InjectMocks
    AuthUserServiceImpl service;

    AuthUser returnedUser;
    AuthUserCreateDto createDto;
    AuthUserUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        returnedUser = AuthUser.builder()
                .id(1)
                .username("Panda")
                .email("panda@gmail.com")
                .password("9779")
                .build();
        createDto = AuthUserCreateDto.builder()
                .username("Easy")
                .email("easy@gmail.com")
                .password("123")
                .build();
        updateDto = AuthUserUpdateDto.builder()
                .id(1)
                .username("Simple")
                .email("simple@gmail.com")
                .build();
    }

    @Test
    void create() {
        when(repository.save(any(AuthUser.class))).thenReturn(AuthUser.builder().username("Easy").build());

        AuthUserGetDto getDto = service.create(createDto);

        assertEquals("Easy", getDto.getUsername());

        verify(repository, times(1)).save(any());
    }

    @Test
    void createWithNullDto() {
        assertThrows(RuntimeException.class, () -> service.create(null));
    }

    @Test
    void update() {
        when(repository.findById(1)).thenReturn(Optional.of(returnedUser));

        when(repository.save(any(AuthUser.class))).thenReturn(new AuthUser(1, updateDto.getUsername(), updateDto.getEmail(), "123"));

        AuthUserGetDto updatedUser = service.update(updateDto);

        assertEquals(updateDto.getEmail(), updatedUser.getEmail());
        assertEquals(updateDto.getUsername(), updatedUser.getUsername());

        verify(repository, atMost(1)).save(any());
        verify(repository, atMost(1)).findById(1);
    }

    @Test
    void updateWithNullDto() {
        assertThrows(RuntimeException.class, () -> service.update(null));
    }

    @Test
    void update_NotFoundException() {
        when(repository.findById(anyInt())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.update(new AuthUserUpdateDto()));
        verify(repository, atMost(1)).findById(anyInt());
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(1);
        service.delete(1);
        assertAll(() -> service.delete(1));
    }

    @Test
    void deleteWithNullId() {
        assertThrows(RuntimeException.class, () -> service.delete(null));
    }

    @Test
    void get() {
        when(repository.findById(1)).thenReturn(Optional.of(returnedUser));
        AuthUserGetDto foundUser = service.get(1);
        assertEquals("Panda", foundUser.getUsername());
        assertEquals("panda@gmail.com", foundUser.getEmail());
    }

    @Test
    void get_NotFoundException() {
        when(repository.findById(anyInt())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> service.get(anyInt()));
        verify(repository, atMost(1)).findById(anyInt());
    }

    @Test
    void getWithNullId() {
        assertThrows(RuntimeException.class, () -> service.get(null));
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(List.of(new AuthUser(), new AuthUser()));

        List<AuthUserGetDto> usersList = service.getAll();
        assertEquals(2, usersList.size());
    }
}