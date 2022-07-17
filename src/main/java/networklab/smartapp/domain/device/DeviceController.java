package networklab.smartapp.domain.device;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "main")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public String getMainPage() {
        return "main";
    }
}
