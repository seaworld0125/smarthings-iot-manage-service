package networklab.smartapp.domain.device.repository;

import networklab.smartapp.domain.device.entity.HourEnergyData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-08-15
 */
public interface HourEnergyDataRepository extends JpaRepository<HourEnergyData, Long> {

}
