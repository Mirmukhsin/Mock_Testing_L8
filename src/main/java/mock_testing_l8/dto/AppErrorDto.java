package mock_testing_l8.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppErrorDto {
    private String errorPath;
    private Integer errorCode;
    private String friendlyMessage;
    private Object developerMessage;
}
