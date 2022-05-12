package networklab.smartapp.app;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void confirmation(String url) {
        String result = restTemplate.getForObject(url, String.class);
    }
}
