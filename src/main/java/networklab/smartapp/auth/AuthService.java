package networklab.smartapp.auth;

import networklab.smartapp.response.success.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<ResponseDto> login(String password);
}