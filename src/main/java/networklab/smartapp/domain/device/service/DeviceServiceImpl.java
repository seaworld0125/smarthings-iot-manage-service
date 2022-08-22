package networklab.smartapp.domain.device.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.device.dto.EnergyDataDto;
import networklab.smartapp.domain.device.repository.DailyEnergyDataRepository;
import networklab.smartapp.domain.device.repository.HourEnergyDataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-07-26
 */
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final HourEnergyDataRepository hourEnergyDataRepository;
    private final DailyEnergyDataRepository dailyEnergyDataRepository;

    @Override
    public List<EnergyDataDto> findDailyEnergyData(String deviceId) {
        return dailyEnergyDataRepository.findAllByDeviceId(deviceId, PageRequest.of(0, 14))
                .stream()
                .map(EnergyDataDto::dataToDto)
                .collect(Collectors.toList());
    }
}
