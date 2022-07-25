package networklab.smartapp.domain.scheduler;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import networklab.smartapp.domain.device.dto.DeviceListDto.DeviceInfo;
import networklab.smartapp.domain.device.dto.SmartPlugDetailDto;
import networklab.smartapp.domain.device.entity.Device;
import networklab.smartapp.domain.device.entity.EnergyData;
import networklab.smartapp.domain.device.repository.EnergyDataRepository;
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
    private final EnergyDataRepository energyDataRepository;

//    @Scheduled(fixedDelay = 3000)
    @Scheduled(cron = "0 30 23 * * *")
    public void scheduleSaveIoTData() {

        List<Member> members = memberRepository.findAll();
        for (var member : members) {
            List<DeviceInfo> deviceInfos = deviceInfoService.getDeviceList(member.getPAT());

            for (var deviceInfo : deviceInfos) {
                SmartPlugDetailDto detailDto = deviceInfoService.getDeviceEnergyConsumptionStatus(member.getPAT(), deviceInfo.getDeviceId());
                Device device = deviceService.findDeviceEntity(deviceInfo.getDeviceId());

                Double dailyConsumption = Double.valueOf(detailDto.getDailyConsumption()
                        .substring(0, detailDto.getDailyConsumption().length() - 4)
                );

                energyDataRepository.save(EnergyData.builder()
                        .device(device)
                        .regDate(new Date())
                        .energyConsumption(dailyConsumption)
                        .build()
                );
            }
        }
    }
}
