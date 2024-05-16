package mock_testing_l8.service;

import lombok.NonNull;
import mock_testing_l8.dto.AuthUserCreateDto;
import mock_testing_l8.dto.AuthUserGetDto;
import mock_testing_l8.dto.AuthUserUpdateDto;
import mock_testing_l8.entity.AuthUser;

import java.util.List;

public interface AuthUserService {
    void create(@NonNull AuthUserCreateDto dto);

    void update(@NonNull AuthUserUpdateDto dto);

    void delete(@NonNull Integer id);

    AuthUserGetDto get(@NonNull Integer id);

    List<AuthUserGetDto> getAll();
}
