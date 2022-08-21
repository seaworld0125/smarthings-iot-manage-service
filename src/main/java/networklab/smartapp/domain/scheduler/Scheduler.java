package networklab.smartapp.domain.scheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import networklab.smartapp.domain.device.dto.DeviceListDto.DeviceInfo;
import networklab.smartapp.domain.device.dto.SmartPlugDetailDto;
import networklab.smartapp.domain.device.entity.DailyEnergyData;
import networklab.smartapp.domain.device.entity.Device;
import networklab.smartapp.domain.device.entity.HourEnergyData;
import networklab.smartapp.domain.device.repository.DailyEnergyDataRepository;
import networklab.smartapp.domain.device.repository.HourEnergyDataRepository;
import networklab.smartapp.domain.device.service.DeviceInfoService;
import networklab.smartapp.domain.device.service.DeviceService;
import networklab.smartapp.domain.member.Member;
import networklab.smartapp.domain.member.MemberRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 태경 2022-07-25
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final DeviceInfoService deviceInfoService;
    private final DeviceService deviceService;
    private final MemberRepository memberRepository;
    private final DailyEnergyDataRepository dailyEnergyDataRepository;
    private final HourEnergyDataRepository hourEnergyDataRepository;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void scheduleSaveDailyData() {

        List<Member> members = memberRepository.findAll();
        for (var member : members) {
            List<DeviceInfo> deviceInfos = deviceInfoService.getDeviceList(member.getPAT());

            for (var deviceInfo : deviceInfos) {
                SmartPlugDetailDto detailDto = deviceInfoService.getDeviceEnergyConsumptionStatus(member.getPAT(), deviceInfo.getDeviceId());
                Device device = deviceService.findDeviceEntityWithHourEnergyData(deviceInfo.getDeviceId());

                Double consumption = Double.valueOf(detailDto.getDailyConsumption()
                        .substring(0, detailDto.getDailyConsumption().length() - 4)
                );

                Optional<HourEnergyData> opt = device.getHourEnergyDataSet().stream()
                        .max(Comparator.comparing(HourEnergyData::getRegDate));

                if(opt.isPresent()) {
                    HourEnergyData lastEnergyData = opt.get();
                    LocalDateTime lastDate = LocalDateTime.ofInstant(lastEnergyData.getRegDate().toInstant(), ZoneId.systemDefault());
                    LocalDateTime now = LocalDateTime.now();

                    if(lastDate.getDayOfMonth() == now.getDayOfMonth()
                            && lastDate.getMonthValue() == now.getMonthValue()
                            && lastDate.getYear() == now.getYear()
                    ) {
                        consumption -= lastEnergyData.getEnergyConsumption();
                    }
                }

                hourEnergyDataRepository.save(HourEnergyData.builder()
                        .device(device)
                        .regDate(new Date())
                        .energyConsumption(consumption)
                        .build()
                );
            }
        }
    }

    @Scheduled(cron = "0 0 0/24 * * *")
    public void scheduleSaveHourData() {

        List<Member> members = memberRepository.findAll();
        for (var member : members) {
            List<DeviceInfo> deviceInfos = deviceInfoService.getDeviceList(member.getPAT());

            for (var deviceInfo : deviceInfos) {
                SmartPlugDetailDto detailDto = deviceInfoService.getDeviceEnergyConsumptionStatus(member.getPAT(), deviceInfo.getDeviceId());
                Device device = deviceService.findDeviceEntityWithHourEnergyData(deviceInfo.getDeviceId());

                Double consumption = Double.valueOf(detailDto.getDailyConsumption()
                        .substring(0, detailDto.getDailyConsumption().length() - 4)
                );

                dailyEnergyDataRepository.save(DailyEnergyData.builder()
                        .device(device)
                        .regDate(new Date())
                        .energyConsumption(consumption)
                        .build()
                );
            }
        }
    }
}
