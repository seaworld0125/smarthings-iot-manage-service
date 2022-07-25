package networklab.smartapp.domain.device.service;

import networklab.smartapp.domain.device.entity.Device;

/**
 * @author 태경 2022-07-26
 */
public interface DeviceService {

    Device findDeviceEntity(String id);
}
