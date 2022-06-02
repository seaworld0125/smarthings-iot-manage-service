package networklab.smartapp.domain.auth;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.error.exception.BusinessException;
import networklab.smartapp.error.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity auth(@RequestBody @Valid AuthDto authDto) {
        boolean result = authService.login(authDto.getPassword());

        if(!result) throw new BusinessException(ErrorCode.PASSWORD_INVALID);
        return new ResponseEntity(HttpStatus.OK);
    }
}