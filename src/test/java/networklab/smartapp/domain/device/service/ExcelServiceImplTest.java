package networklab.smartapp.domain.device.service;

import networklab.smartapp.domain.device.entity.DailyEnergyData;
import networklab.smartapp.domain.device.entity.HourEnergyData;
import networklab.smartapp.domain.device.repository.DailyEnergyDataRepository;
import networklab.smartapp.domain.device.repository.HourEnergyDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootTest
class ExcelServiceImplTest {

    @Autowired
    private DailyEnergyDataRepository dailyEnergyDataRepository;

    @Autowired
    private HourEnergyDataRepository hourEnergyDataRepository;

    @Test
    void save() {
        Random random = new Random();

        int dd = 1;
        while(dd < 22) {
            int hh = 0;
            while(hh < 24) {
                hourEnergyDataRepository.save(HourEnergyData.builder()
                        .deviceId("df3e6530-b638-4b72-b750-09048a98c020")
                        .regDate(LocalDateTime.of(2022, 8, dd, hh, 0))
                        .energyConsumption(random.nextDouble())
                        .build()
                );
                hh++;
            }
            dd++;
        }
    }

    @Test
    void save2() {
        Random random = new Random();

        int dd = 1;
        while(dd < 22) {
            int hh = 0;
            dailyEnergyDataRepository.save(DailyEnergyData.builder()
                    .deviceId("df3e6530-b638-4b72-b750-09048a98c020")
                    .regDate(LocalDateTime.of(2022, 8, dd, 0, 0))
                    .energyConsumption(random.nextDouble())
                    .build()
            );
            dd++;
        }
    }

    @Test
    @Transactional
    void find() {
        String deviceId = "df3e6530-b638-4b72-b750-09048a98c020";
        LocalDateTime date = LocalDateTime.of(2022, 8, 18, 1, 0);
        Stream<HourEnergyData> dataStream = hourEnergyDataRepository.findAllByDeviceIdAndDateUsingStream(deviceId, date);

        dataStream.forEach(System.out::println);
    }
}