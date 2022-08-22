package networklab.smartapp.domain.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import networklab.smartapp.domain.device.dto.DeviceListDto.DeviceInfo;
import networklab.smartapp.domain.device.dto.SmartPlugDetailDto;
import networklab.smartapp.domain.device.entity.DailyEnergyData;
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

    @Scheduled(cron = "0 0 0/1 * * *", zone = "Asia/Seoul")
    public void scheduleSaveHourData() {

        List<Member> members = memberRepository.findAll();
        for (var member : members) {
            List<DeviceInfo> deviceInfos = deviceInfoService.getDeviceList(member.getPAT());

            for (var deviceInfo : deviceInfos) {
                SmartPlugDetailDto detailDto = deviceInfoService.getDeviceEnergyConsumptionStatus(member.getPAT(), deviceInfo.getDeviceId());

                Double consumption = Double.valueOf(detailDto.getDailyConsumption()
                        .substring(0, detailDto.getDailyConsumption().length() - 4)
                );

                LocalDateTime now = LocalDateTime.now().withMinute(0);
                LocalDateTime lastDate = now.minusHours(1);

                Optional<HourEnergyData> opt = hourEnergyDataRepository.findLast(deviceInfo.getDeviceId(), lastDate);
                if(opt.isPresent()) {
                    HourEnergyData lastEnergyData = opt.get();
                    consumption -= lastEnergyData.getEnergyConsumption();
                }
                hourEnergyDataRepository.save(HourEnergyData.builder()
                        .deviceId(deviceInfo.getDeviceId())
                        .regDate(now)
                        .energyConsumption(consumption)
                        .build()
                );
            }
        }
    }

    @Scheduled(cron = "0 0 0/24 * * *", zone = "Asia/Seoul")
    public void scheduleSaveDailyData() {

        List<Member> members = memberRepository.findAll();
        for (var member : members) {
            List<DeviceInfo> deviceInfos = deviceInfoService.getDeviceList(member.getPAT());

            for (var deviceInfo : deviceInfos) {
                SmartPlugDetailDto detailDto = deviceInfoService.getDeviceEnergyConsumptionStatus(member.getPAT(), deviceInfo.getDeviceId());

                Double consumption = Double.valueOf(detailDto.getDailyConsumption()
                        .substring(0, detailDto.getDailyConsumption().length() - 4)
                );

                LocalDateTime dateTime = LocalDateTime.now().withHour(0).withMinute(0);

                dailyEnergyDataRepository.save(DailyEnergyData.builder()
                        .deviceId(deviceInfo.getDeviceId())
                        .regDate(dateTime)
                        .energyConsumption(consumption)
                        .build()
                );
            }
        }
    }
}
