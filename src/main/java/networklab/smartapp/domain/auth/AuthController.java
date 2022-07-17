package networklab.smartapp.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String getAuthPage(@RequestParam(value = "error", defaultValue = "false") boolean error, Model model) {

        model.addAttribute("error", error);
        return "login";
    }

//    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
//    public String auth(@RequestParam MultiValueMap<String,String> paramMap, HttpServletRequest request, HttpServletResponse response) {
//
//        if(!(paramMap.containsKey("username") || paramMap.containsKey("password"))) {
//            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
//        }
//        authService.authVerify(paramMap.getFirst("username"), paramMap.getFirst("password"));
//        HttpSession session = request.getSession(false);
//
//        session.setAttribute("username", paramMap.getFirst("username"));
//        return "redirect:/main";
//    }

    @GetMapping("/join")
    public String getJoinPage() { return "join"; }

    @PostMapping(value = "/join", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String join(@RequestParam MultiValueMap<String,String> paramMap) {

        authService.joinVerify(paramMap.getFirst("pat"), paramMap.getFirst("username"), paramMap.getFirst("password"));
        return "login";
    }

    @GetMapping("/expired-session")
    public String getExpiredSessionPage() { return "expired-session"; }

    @GetMapping("/invalid-session")
    public String getInvalidSessionPage() { return "invalid-session"; }
}