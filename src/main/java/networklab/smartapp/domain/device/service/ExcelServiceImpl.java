package networklab.smartapp.domain.device.service;

import lombok.RequiredArgsConstructor;
import networklab.smartapp.domain.device.entity.HourEnergyData;
import networklab.smartapp.domain.device.repository.HourEnergyDataRepository;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final HourEnergyDataRepository hourEnergyDataRepository;

    @Override
    @Transactional
    public SXSSFWorkbook createExcelFile(String deviceId, LocalDateTime date) {

        // new workbook
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("test sheet");

        // top row
        Row topRow = sheet.createRow(0);
        topRow.createCell(0).setCellValue("date");
        topRow.createCell(1).setCellValue("time");
        topRow.createCell(2).setCellValue("kWh");
        topRow.createCell(3).setCellValue("device-type");

        // get db stream
        Stream<HourEnergyData> dataStream = hourEnergyDataRepository.findAllByDeviceIdAndDateUsingStream(deviceId, date);

        // add row
        AtomicInteger count = new AtomicInteger(1);
        dataStream.forEach(H -> {
            Row row = sheet.createRow(count.getAndIncrement());
            row.createCell(0).setCellValue(H.getRegDate().toLocalDate().toString());
            row.createCell(1).setCellValue(H.getRegDate().toLocalTime().toString());
            row.createCell(2).setCellValue(H.getEnergyConsumption().toString());
            row.createCell(3).setCellValue("plug");
        });

        return workbook;
    }
}
