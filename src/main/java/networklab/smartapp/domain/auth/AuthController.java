package networklab.smartapp.domain.auth;

import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.dto.JoinDto;
import networklab.smartapp.domain.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public String getAuthPage() {
        return "auth";
    }

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String auth(@RequestParam MultiValueMap<String,String> paramMap) {

        authService.authVerify(paramMap.getFirst("username"), paramMap.getFirst("password"));
        return "main";
    }

    @GetMapping("/join")
    public String getJoinPage() { return "join"; }

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> join(JoinDto joinDto) {

        authService.joinVerify(joinDto);
        return new ResponseEntity<>(ResponseDto.of("회원가입 성공"), HttpStatus.OK);
    }
}