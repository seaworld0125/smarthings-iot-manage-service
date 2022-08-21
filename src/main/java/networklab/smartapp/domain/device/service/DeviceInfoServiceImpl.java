package networklab.smartapp.domain.device.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import networklab.smartapp.domain.device.RequestUrl;
import networklab.smartapp.domain.device.dto.DeviceListDto;
import networklab.smartapp.domain.device.dto.DeviceListDto.DeviceInfo;
import networklab.smartapp.domain.device.dto.SmartPlugDetailDto;
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
public class DeviceInfoServiceImpl implements DeviceInfoService {

    private final RestTemplate restTemplate;

    private <T> ResponseEntity<T> restExchange(String requestUrl, HttpMethod method, HttpEntity entity, Class<T> responseType) {
        return restTemplate.exchange(
               requestUrl,
               method,
               entity,
               responseType
        );
    }

    @Override
    public List<DeviceInfo> getDeviceList(String pat) {
        String requestUrl = RequestUrl.DEVICE_LIST.getUrl();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + pat);

        HttpHeaders httpHeaders = new HttpHeaders(headers);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<DeviceListDto> responseEntity = restExchange(requestUrl, HttpMethod.GET, httpEntity, DeviceListDto.class);
        DeviceListDto deviceListDto = responseEntity.getBody();
        if(deviceListDto != null) {
            var deviceInfoList = deviceListDto.getItems();
            return deviceInfoList.stream().filter(D -> !D.getName().equals("SmartThings Hub")).collect(Collectors.toList());
        }
        return new ArrayList<DeviceInfo>();
    }

    @Override
    public List<DeviceInfo> getDevicesFullStatus(String pat, List<DeviceInfo> deviceInfos) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + pat);

        HttpHeaders httpHeaders = new HttpHeaders(headers);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        for(DeviceInfo deviceInfo : deviceInfos) {
            try {
                JSONParser jsonParser = new JSONParser();
                String requestUrl = RequestUrl.getDeviceSwitchStatusUrl(deviceInfo.getDeviceId());
                ResponseEntity<String> responseEntity = restExchange(requestUrl, HttpMethod.GET, httpEntity, String.class);
                String res = responseEntity.getBody();

                var aSwitch = (JSONObject) jsonParser.parse(res);
                aSwitch = (JSONObject) aSwitch.get("switch");

                String switchValue = aSwitch.get("value").toString();
                deviceInfo.setSwitchValue(switchValue);

                System.out.println(deviceInfo);
            } catch (ParseException parseException) {
                log.warn(parseException.getMessage());
            }
        }
        return deviceInfos;
    }

    @Override
    public SmartPlugDetailDto getDeviceEnergyConsumptionStatus(String pat, String deviceId) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + pat);

        HttpHeaders httpHeaders = new HttpHeaders(headers);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        try {
            JSONParser jsonParser = new JSONParser();
            String requestUrl = RequestUrl.getDeviceEnergyConsumptionStatus(deviceId);
            ResponseEntity<String> responseEntity = restExchange(requestUrl, HttpMethod.GET, httpEntity, String.class);
            String res = responseEntity.getBody();

            var resJson = (JSONObject) jsonParser.parse(res);
            var power = (JSONObject) ((JSONObject) resJson.get("powerMeter")).get("power");
            var energy = (JSONObject) ((JSONObject) resJson.get("energyMeter")).get("energy");

            String currentConsumption = power.get("value").toString() + power.get("unit").toString();
            String dailyConsumption = energy.get("value").toString() + energy.get("unit").toString();

            return new SmartPlugDetailDto(deviceId, currentConsumption, dailyConsumption);
        } catch (ParseException parseException) {
            log.warn(parseException.getMessage());
        }
        return new SmartPlugDetailDto(deviceId, "error", "error");
    }
}
