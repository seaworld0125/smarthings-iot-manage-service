package networklab.smartapp.device;

import lombok.RequiredArgsConstructor;
import networklab.smartapp.response.success.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "device")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping(value = "list")
    public ResponseEntity<ResponseDto> deviceList() {
        return deviceService.getDeviceList();
    }
}
