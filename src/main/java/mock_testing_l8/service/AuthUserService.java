package mock_testing_l8.service;

import lombok.NonNull;
import mock_testing_l8.dto.AuthUserCreateDto;
import mock_testing_l8.dto.AuthUserGetDto;
import mock_testing_l8.dto.AuthUserUpdateDto;

import java.util.List;

public interface AuthUserService {
    AuthUserGetDto create(@NonNull AuthUserCreateDto dto);

    AuthUserGetDto update(@NonNull AuthUserUpdateDto dto);

    void delete(@NonNull Integer id);

    AuthUserGetDto get(@NonNull Integer id);

    List<AuthUserGetDto> getAll();
}
