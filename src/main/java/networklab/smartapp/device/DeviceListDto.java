package networklab.smartapp.device;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceListDto {

    private List<Device> items;

    @Getter
    public static class Device {
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
    }

    public List<Device> getItems() {
        return items.subList(1, items.size());
    }
}
