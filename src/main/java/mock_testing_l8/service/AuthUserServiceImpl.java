package mock_testing_l8.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mock_testing_l8.dto.AuthUserCreateDto;
import mock_testing_l8.dto.AuthUserGetDto;
import mock_testing_l8.dto.AuthUserUpdateDto;
import mock_testing_l8.entity.AuthUser;
import mock_testing_l8.repository.AuthUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final AuthUserRepository repository;

    @Override
    public AuthUserGetDto create(@NonNull AuthUserCreateDto dto) {
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        AuthUser savedUser = repository.save(authUser);

        return new AuthUserGetDto(savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    public AuthUserGetDto update(@NonNull AuthUserUpdateDto dto) {
        AuthUser authUser = repository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        authUser.setUsername(dto.getUsername());
        authUser.setEmail(dto.getEmail());

        AuthUser savedUser = repository.save(authUser);

        return new AuthUserGetDto(savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    public void delete(@NonNull Integer id) {
        repository.deleteById(id);
    }

    @Override
    public AuthUserGetDto get(@NonNull Integer id) {
        AuthUser authUser = repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        return new AuthUserGetDto(authUser.getUsername(), authUser.getEmail());
    }

    @Override
    public List<AuthUserGetDto> getAll() {
        List<AuthUser> authUsers = repository.findAll();
        List<AuthUserGetDto> userGetDtoList = new ArrayList<>();
        for (AuthUser authUser : authUsers) {
            userGetDtoList.add(new AuthUserGetDto(authUser.getUsername(), authUser.getEmail()));
        }
        return userGetDtoList;
    }
}
