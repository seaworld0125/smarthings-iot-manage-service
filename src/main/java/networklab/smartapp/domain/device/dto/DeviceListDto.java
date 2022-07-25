package networklab.smartapp.domain.device.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceListDto {

    private List<DeviceInfo> items;

    @Getter
    @ToString
    public static class DeviceInfo {
        private String deviceId;
        private String name;
        private String label;
        private String manufacturerName;
        private String presentationId;
        private String locationId;
        private String roomId;
        private String createTime;
        private String type;
        private String restrictionTier;

        @Setter
        private String switchValue;
    }
}
