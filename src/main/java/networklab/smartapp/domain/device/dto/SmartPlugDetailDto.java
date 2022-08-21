package networklab.smartapp.domain.device.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 태경 2022-07-25
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmartPlugDetailDto {

    private String deviceId;

    private String currentConsumption;

    private String dailyConsumption;

}
