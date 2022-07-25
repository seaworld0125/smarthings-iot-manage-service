package networklab.smartapp.domain.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RequestUrl {
    DEVICE_LIST("https://api.smartthings.com/v1/devices"),
    ;
    private final String url;

    public static String getDeviceSwitchStatusUrl(String deviceId) {
        return "https://api.smartthings.com/v1/devices/" + deviceId + "/components/main/capabilities/switch/status";
    }

    public static String getDeviceEnergyConsumptionStatus(String deviceId) {
        return "https://api.smartthings.com/v1/devices/" + deviceId + "/components/main/status";
    }
}
