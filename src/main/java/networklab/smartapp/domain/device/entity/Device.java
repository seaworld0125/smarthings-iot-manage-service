package networklab.smartapp.domain.device.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 태경 2022-07-26
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Device {

    @Id
    private String id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "device")
    @Default
    Set<EnergyData> energyDataSet = new HashSet<>();
}
