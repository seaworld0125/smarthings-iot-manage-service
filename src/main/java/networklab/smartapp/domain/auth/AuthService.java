package networklab.smartapp.domain.auth;

public interface AuthService {

    void authVerify(String username, String password);

    void joinVerify(String pat, String username, String password);
}