package networklab.smartapp.device;

import java.util.List;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.device.DeviceListDto.Device;
import networklab.smartapp.response.success.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static networklab.smartapp.device.RequestUrl.*;

@Component
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final RestTemplate restTemplate;

    @Value("${user.pat}")
    private String pat;

    @Override
    public ResponseEntity<ResponseDto> getDeviceList() {
        String requestUrl = DEVICE_LIST.getUrl();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + pat);

        HttpHeaders httpHeaders = new HttpHeaders(headers);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<DeviceListDto> responseEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                httpEntity,
                DeviceListDto.class
                );

        DeviceListDto deviceListDto = responseEntity.getBody();

        assert deviceListDto != null;
        List<Device> devicesList = deviceListDto.getItems();

        for(Device device : devicesList) {
            System.out.println(device.getName());
        }

        return null;
    }
}
