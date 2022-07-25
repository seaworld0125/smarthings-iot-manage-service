package networklab.smartapp.domain.device.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.auth.CustomUserDetails;
import networklab.smartapp.domain.device.dto.DeviceListDto.DeviceInfo;
import networklab.smartapp.domain.device.dto.EnergyDataDto;
import networklab.smartapp.domain.device.dto.SmartPlugDetailDto;
import networklab.smartapp.domain.device.entity.Device;
import networklab.smartapp.domain.device.service.DeviceInfoService;
import networklab.smartapp.domain.device.service.DeviceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "main")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceInfoService deviceInfoService;

    @GetMapping
    public String getMainPage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        List<DeviceInfo> deviceList = deviceInfoService.getDeviceList(userDetails.getPat());
        deviceList = deviceInfoService.getDevicesFullStatus(userDetails.getPat(), deviceList);

        model.addAttribute("deviceList", deviceList);
        model.addAttribute("pat", userDetails.getPat());
        return "main";
    }

    @GetMapping("/detail/{deviceId}")
    public String getDetailPage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable(value = "deviceId") String deviceId,
            Model model
    ) {
        SmartPlugDetailDto smartPlugDetailDto = deviceInfoService.getDeviceEnergyConsumptionStatus(userDetails.getPat(), deviceId);
        Device device = deviceService.findDeviceEntity(deviceId);
        List<EnergyDataDto> energyDataDtoList = device.getEnergyDataSet()
                        .stream()
                        .sorted((e1, e2) -> e2.getRegDate().compareTo(e1.getRegDate()))
                        .map(EnergyDataDto::dataToDto)
                        .collect(Collectors.toList());

        model.addAttribute("plugDetail", smartPlugDetailDto);
        model.addAttribute("energyDataDtoList", energyDataDtoList);
        System.out.println(energyDataDtoList);
        return "smart-plug";
    }
}
