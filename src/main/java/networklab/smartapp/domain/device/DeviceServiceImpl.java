package networklab.smartapp.domain.device;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.device.DeviceListDto.Device;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final RestTemplate restTemplate;

    @Override
    public List<Device> getDeviceList(String pat) {
        String requestUrl = RequestUrl.DEVICE_LIST.getUrl();

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
        return deviceListDto != null ? deviceListDto.getItems() : new ArrayList<Device>();
    }
}
