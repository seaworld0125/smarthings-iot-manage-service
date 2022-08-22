package networklab.smartapp.domain.device.service;

import networklab.smartapp.domain.device.dto.EnergyDataDto;
import java.util.List;

/**
 * @author 태경 2022-07-26
 */
public interface DeviceService {

    public List<EnergyDataDto> findDailyEnergyData(String deviceId);
}
