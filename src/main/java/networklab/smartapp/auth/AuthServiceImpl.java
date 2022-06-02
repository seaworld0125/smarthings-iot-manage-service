package networklab.smartapp.auth;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${user.password}")
    private String password;

    @Override
    public boolean login(String password) {
        return Objects.equals(this.password, password);
    }
}
