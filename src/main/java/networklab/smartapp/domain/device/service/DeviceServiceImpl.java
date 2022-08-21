package networklab.smartapp.domain.device.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.device.entity.Device;
import networklab.smartapp.domain.device.repository.DeviceRepository;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-07-26
 */
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public Device findDeviceEntityWithDailyEnergyData(String id) {
        Optional<Device> deviceOptional = deviceRepository.getDeviceByIdWithDailyEnergyData(id);
        if(deviceOptional.isEmpty()) {
            return deviceRepository.save(Device.builder()
                    .id(id)
                    .build()
            );
        }
        return deviceOptional.get();
    }

    @Override
    public Device findDeviceEntityWithHourEnergyData(String id) {
        Optional<Device> deviceOptional = deviceRepository.getDeviceByIdWithHourEnergyData(id);
        if(deviceOptional.isEmpty()) {
            return deviceRepository.save(Device.builder()
                    .id(id)
                    .build()
            );
        }
        return deviceOptional.get();
    }

    @Override
    public Device findById(String id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if(deviceOptional.isEmpty()) {
            return deviceRepository.save(Device.builder()
                    .id(id)
                    .build()
            );
        }
        return deviceOptional.get();
    }
}
