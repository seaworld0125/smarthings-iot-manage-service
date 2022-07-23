package networklab.smartapp.domain.device;

import java.util.List;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.auth.CustomUserDetails;
import networklab.smartapp.domain.device.DeviceListDto.Device;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "main")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public String getMainPage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {
        List<Device> deviceList = deviceService.getDeviceList(userDetails.getPat());
        System.out.println(deviceList);
        model.addAttribute("deviceList", deviceList);
        return "main";
    }
}
