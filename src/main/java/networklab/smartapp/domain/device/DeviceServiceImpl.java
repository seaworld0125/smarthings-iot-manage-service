package networklab.smartapp.domain.device;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import networklab.smartapp.domain.device.DeviceListDto.Device;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
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

    @Override
    public List<Device> getDevicesFullStatus(String pat, List<Device> devices) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + pat);

        HttpHeaders httpHeaders = new HttpHeaders(headers);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        for(Device device : devices) {
            try {
                JSONParser jsonParser = new JSONParser();
                String requestUrl = RequestUrl.getDeviceSwitchStatusUrl(device.getDeviceId());
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        requestUrl,
                        HttpMethod.GET,
                        httpEntity,
                        String.class
                );
                String res = responseEntity.getBody();
                JSONObject aSwitch = (JSONObject) jsonParser.parse(res);
                aSwitch = (JSONObject) aSwitch.get("switch");
                String switchValue = aSwitch.get("value").toString();
                device.setSwitchValue(switchValue);

                System.out.println(device);
            } catch (ParseException parseException) {
                log.warn(parseException.getMessage());
            }
        }
        return devices;
    }
}
