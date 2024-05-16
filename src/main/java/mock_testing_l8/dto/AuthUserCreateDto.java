package mock_testing_l8.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mock_testing_l8.entity.AuthUser;

import java.io.Serializable;

/**
 * DTO for {@link AuthUser}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserCreateDto implements Serializable {
    @NotBlank(message = "username can not be blank")
    private String username;
    @NotBlank(message = "email can not be blank")
    private String email;
    @NotBlank(message = "password can not be blank")
    private String password;
}