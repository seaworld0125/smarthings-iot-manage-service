package networklab.smartapp.domain.device.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.auth.CustomUserDetails;
import networklab.smartapp.domain.device.dto.DeviceListDto.DeviceInfo;
import networklab.smartapp.domain.device.dto.EnergyDataDto;
import networklab.smartapp.domain.device.dto.SmartPlugDetailDto;
import networklab.smartapp.domain.device.service.DeviceInfoService;
import networklab.smartapp.domain.device.service.DeviceService;
import networklab.smartapp.domain.device.service.ExcelService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ExcelService excelService;

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
        List<EnergyDataDto> energyDataDtoList = deviceService.findDailyEnergyData(deviceId);

        model.addAttribute("plugDetail", smartPlugDetailDto);
        model.addAttribute("energyDataDtoList", energyDataDtoList);
        System.out.println(energyDataDtoList);
        return "smart-plug";
    }

    /*
    * DeviceId가 고유 Id가 아닌듯...
    * 중간에 바뀐 것으로 보이는데 확인이 필요함
    * */

    @GetMapping("/excel/{deviceId}/{dateString}")
    public ResponseEntity<String> getExcelfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable(value = "deviceId") String deviceId,
            @PathVariable(value = "dateString") String dateString,
            HttpServletRequest request,
            HttpServletResponse response
    )
            throws IOException {

        // request date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime requestDate = LocalDateTime.parse(dateString, formatter).withMinute(0); // 0분으로 초기화

        // new workbook
        SXSSFWorkbook workbook = excelService.createExcelFile(deviceId, requestDate);

        // 파일명 설정
        LocalDateTime now = LocalDateTime.now();
        String fileName = "파일명" + "_" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")) + ".xlsx";

        // 브라우저 얻기
        String browser = request.getHeader("User-Agent");

        // 브라우저에 따른 파일명 인코딩 설정
        if (browser.contains("MSIE")) {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.contains("Trident")) {       // IE11
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.contains("Firefox")) {
            fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.contains("Opera")) {
            fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.contains("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < fileName.length(); i++) {
                char c = fileName.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            fileName = sb.toString();
        } else if (browser.contains("Safari")){
            fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
        } else {
            fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
        }

        // 브라우저에 따른 컨텐츠타입 설정
        String contentType = "application/download;charset=utf-8";
        switch(browser) {
            case "Firefox":
            case "Opera":
                contentType = "application/octet-stream; text/html; charset=UTF-8;";
                break;
            default: // MSIE, Trident, Chrome, ..
                contentType = "application/x-msdownload; text/html; charset=UTF-8;";
                break;
        }
        response.setContentType("application/x-msdownload; text/html; charset=UTF-8;"); // msie, tRIDE
        response.setContentType("application/octet-stream; text/html; charset=UTF-8;");

        // response 설정
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        // 파일 생성 : OutputStream을 얻어 생성한 엑셀 write
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

        return new ResponseEntity<String>(new String("ok"), HttpStatus.OK);
    }
}
