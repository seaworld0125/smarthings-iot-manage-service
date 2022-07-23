package networklab.smartapp.domain.device;

import java.util.List;
import networklab.smartapp.domain.device.DeviceListDto.Device;

public interface DeviceService {

    public List<Device> getDeviceList(String pat);
}
