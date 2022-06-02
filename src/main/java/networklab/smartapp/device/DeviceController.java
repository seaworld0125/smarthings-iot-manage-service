package networklab.smartapp.device;

import java.util.List;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.device.DeviceListDto.Device;
import networklab.smartapp.response.success.ResponseDto;
import networklab.smartapp.response.success.SuccessCode;
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
    public ResponseEntity<ResponseDto<List<Device>>> deviceList() {

        List<Device> deviceList = deviceService.getDeviceList();
        ResponseDto<List<Device>> responseDto = ResponseDto.of(SuccessCode.GET_DEVICE_LIST_SUCCESS, deviceList);

        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(responseDto.getStatus()));
    }
}
