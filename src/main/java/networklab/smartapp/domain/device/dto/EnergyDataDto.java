package networklab.smartapp.domain.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import networklab.smartapp.domain.device.entity.EnergyData;

/**
 * @author 태경 2022-07-26
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
public class EnergyDataDto {
    private String date;
    Double energyConsumption;

    public static EnergyDataDto dataToDto(EnergyData energyData) {
        String date = energyData.getRegDate().toString();
        date = date.substring(0, 4) + '/' + date.substring(5, 7) + '/' + date.substring(8, 10);

        return EnergyDataDto.builder()
                .date(date)
                .energyConsumption(energyData.getEnergyConsumption())
                .build();
    }
}