package networklab.smartapp.auth;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.response.success.ResponseDto;
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
    public ResponseEntity<ResponseDto> auth(@RequestBody @Valid AuthDto authDto) {
        return authService.login(authDto.getPassword());
    }
}