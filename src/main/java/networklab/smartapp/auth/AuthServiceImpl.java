package networklab.smartapp.auth;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.response.error.exception.BusinessException;
import networklab.smartapp.response.error.exception.ErrorCode;
import networklab.smartapp.response.success.ResponseDto;
import networklab.smartapp.response.success.SuccessCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${user.password}")
    private String password;

    @Override
    public ResponseEntity<ResponseDto> login(String password) {
        if(!Objects.equals(this.password, password))
            throw new BusinessException(ErrorCode.PASSWORD_INVALID);

        return new ResponseEntity<>(new ResponseDto(SuccessCode.LOGIN_SUCCESS), HttpStatus.valueOf(SuccessCode.LOGIN_SUCCESS.getStatus()));
    }
}
