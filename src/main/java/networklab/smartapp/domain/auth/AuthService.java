package networklab.smartapp.domain.auth;

import networklab.smartapp.domain.dto.AuthDto;
import networklab.smartapp.domain.dto.JoinDto;

public interface AuthService {

    boolean authVerify(AuthDto authDto);

    boolean joinVerify(JoinDto joinDto);
}