package networklab.smartapp.domain.device;

import java.util.List;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.device.DeviceListDto.Device;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Device>> deviceList() {

        List<Device> deviceList = deviceService.getDeviceList();
        return new ResponseEntity<>(deviceList, HttpStatus.OK);
    }
}
