package networklab.smartapp.device;

import networklab.smartapp.response.success.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface DeviceService {

    public ResponseEntity<ResponseDto> getDeviceList();
}
