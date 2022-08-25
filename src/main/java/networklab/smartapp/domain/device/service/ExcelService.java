package networklab.smartapp.domain.device.service;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.time.LocalDateTime;

public interface ExcelService {

    public SXSSFWorkbook createExcelFile(String deviceId, LocalDateTime startDate, LocalDateTime endDate);
}
