package networklab.smartapp.domain.scheduler;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import networklab.smartapp.domain.device.entity.DailyEnergyData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author 태경 2022-08-15
 */
class SchedulerTest {

    @Test
    void test() {

        final int SET_SIZE = 10;
        Set<DailyEnergyData> testSet = new HashSet<>();
        for (int i = 0; i < SET_SIZE; i++) {
            testSet.add(DailyEnergyData.builder().regDate(new Date(i, i, i)).energyConsumption((double) i).build());
        }

        Optional<DailyEnergyData> opt = testSet.stream().max(Comparator.comparing(DailyEnergyData::getRegDate));
        Assertions.assertThat(opt).isPresent();
        System.out.println(opt.get());
    }

    @Test
    void test1() {

        Random random = new Random();
        List<Integer> test = Stream.generate(random::nextInt).limit(10).collect(Collectors.toList());

        test = test.stream().sorted(Integer::compare).limit(3).collect(Collectors.toList());

        for(var i : test) {
            System.out.println(i);
        }
    }

    @Test
    void test2() {
        LocalDate now = LocalDate.now();
        System.out.println(now);

        System.out.println(now.getDayOfMonth());
        System.out.println(now.getMonthValue());
        System.out.println(now.getYear());
    }
}