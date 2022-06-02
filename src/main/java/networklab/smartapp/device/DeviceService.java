package networklab.smartapp.device;

import java.util.List;
import networklab.smartapp.device.DeviceListDto.Device;
import networklab.smartapp.response.success.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface DeviceService {

    public List<Device> getDeviceList();
}
