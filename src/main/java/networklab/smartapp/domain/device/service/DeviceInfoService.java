package networklab.smartapp.domain.device.service;

import java.util.List;
import networklab.smartapp.domain.device.dto.DeviceListDto.DeviceInfo;
import networklab.smartapp.domain.device.dto.SmartPlugDetailDto;

public interface DeviceInfoService {

    public List<DeviceInfo> getDeviceList(String pat);

    public List<DeviceInfo> getDevicesFullStatus(String pat, List<DeviceInfo> deviceInfos);

    public SmartPlugDetailDto getDeviceEnergyConsumptionStatus(String pat, String deviceId);
}
