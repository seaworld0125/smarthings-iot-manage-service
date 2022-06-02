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
}
