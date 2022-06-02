package networklab.smartapp.response.success;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SuccessCode {
    //    Auth
    SIGN_SUCCESS("회원가입 성공", 200),
    LOGIN_SUCCESS("로그인 성공", 200),
    LOGOUT_SUCCESS("로그아웃 성공", 200),
    CHANGE_KEY_SUCCESS("비밀번호 변경 성공", 200),


    ;

    private final String message;
    private final int status;
}