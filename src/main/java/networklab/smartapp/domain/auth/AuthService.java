package networklab.smartapp.domain.auth;

import networklab.smartapp.domain.dto.JoinDto;

public interface AuthService {

    boolean authVerify(String username, String password);

    boolean joinVerify(JoinDto joinDto);
}