package networklab.smartapp.domain.device.repository;

import io.lettuce.core.dynamic.annotation.Param;
import networklab.smartapp.domain.device.entity.Device;
import networklab.smartapp.domain.device.entity.HourEnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

/**
 * @author 태경 2022-08-15
 */
public interface HourEnergyDataRepository extends JpaRepository<HourEnergyData, Long> {

    @Query("select h from HourEnergyData h where h.device = :device and h.regDate")
    Stream<HourEnergyData> findAllByDeviceUsingStream(@Param("device") Device device);
}
