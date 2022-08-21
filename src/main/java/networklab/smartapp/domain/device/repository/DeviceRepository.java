package networklab.smartapp.domain.device.repository;

import java.util.Optional;
import networklab.smartapp.domain.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 태경 2022-07-26
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    @Query("select distinct d from Device as d left join fetch d.dailyEnergyDataSet where d.id = :id")
    Optional<Device> getDeviceByIdWithDailyEnergyData(@Param("id") String id);

    @Query("select distinct d from Device as d left join fetch d.hourEnergyDataSet where d.id = :id")
    Optional<Device> getDeviceByIdWithHourEnergyData(@Param("id") String id);

    Optional<Device> findById(String id);
}
