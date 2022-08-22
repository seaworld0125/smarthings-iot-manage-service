package networklab.smartapp.domain.device.repository;

import networklab.smartapp.domain.device.entity.HourEnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author 태경 2022-08-15
 */
public interface HourEnergyDataRepository extends JpaRepository<HourEnergyData, Long> {

    @Query("select h from HourEnergyData h where h.deviceId = :deviceId and h.regDate >= :date order by h.regDate desc")
    Stream<HourEnergyData> findAllByDeviceIdAndDateUsingStream(@Param("deviceId") String deviceId, @Param("date") LocalDateTime date);

    @Query("select h from HourEnergyData h where h.deviceId = :deviceId and h.regDate = :date")
    Optional<HourEnergyData> findLast(@Param("deviceId") String deviceId, @Param("date") LocalDateTime date);
}
