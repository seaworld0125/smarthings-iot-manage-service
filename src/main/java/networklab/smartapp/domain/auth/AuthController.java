package networklab.smartapp.domain.auth;

import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.dto.AuthDto;
import networklab.smartapp.domain.dto.JoinDto;
import networklab.smartapp.domain.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public String getAuthPage() {
        return "auth";
    }

    @PostMapping
    public ResponseEntity<ResponseDto> auth(AuthDto authDto) {

        authService.authVerify(authDto);
        return new ResponseEntity<>(ResponseDto.of("로그인 성공"), HttpStatus.OK);
    }

    @GetMapping("/join")
    public String getJoinPage() { return "join"; }

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> join(JoinDto joinDto) {

        authService.joinVerify(joinDto);
        return new ResponseEntity<>(ResponseDto.of("회원가입 성공"), HttpStatus.OK);
    }
}