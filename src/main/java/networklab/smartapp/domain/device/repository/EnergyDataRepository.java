package networklab.smartapp.domain.device.repository;

import networklab.smartapp.domain.device.entity.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 태경 2022-07-26
 */
@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, Long> {

}
