package networklab.smartapp.domain.device.repository;

import networklab.smartapp.domain.device.entity.DailyEnergyData;
import networklab.smartapp.domain.device.entity.HourEnergyData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author 태경 2022-07-26
 */
@Repository
public interface DailyEnergyDataRepository extends JpaRepository<DailyEnergyData, Long> {

    @Query("select d from DailyEnergyData d where d.deviceId = :deviceId order by d.regDate desc")
    List<DailyEnergyData> findAllByDeviceId(@Param("deviceId") String deviceId, Pageable pageable);
}
