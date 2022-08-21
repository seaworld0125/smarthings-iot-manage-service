package networklab.smartapp.domain.device.repository;

import networklab.smartapp.domain.device.entity.DailyEnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 태경 2022-07-26
 */
@Repository
public interface DailyEnergyDataRepository extends JpaRepository<DailyEnergyData, Long> {

}
