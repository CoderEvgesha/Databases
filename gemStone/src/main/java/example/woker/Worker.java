package example.worker;

import example.service.DayService;
import example.service.OperationService;
import example.service.ReasonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class Worker {

    @Autowired
    private DayService dayService;

    @Autowired
    private ReasonService reasonService;

    @Autowired
    private OperationService operationService;

    public void createWorkOperation(String name, String initiator, List<Map.Entry<String, String>> reasons) {
        log.info("start work");
        Optional<GbjObject> dayOptional = dayService.getLastOpenDay();
        GbjObject day;
        if (dayOptional.isEmpty()) {
            Optional<GbjObject> newDay = dayService.createDay();
            day = newDay.orElseThrow();
            dayService.openDay(day);
        } else {
            day = dayOptional.get();
        }
        List<GbjObject> reasonStubs = new ArrayList<>();
        reasons.forEach(map -> {
            Optional<GbjObject> newReason = reasonService.createReason(map.getKey(), map.getValue());
            GbjObject reason = newReason.orElseThrow();
            reasonStubs.add(reason);
        });
        operationService.createOperation(name, initiator, reasonStubs);
        dayService.closeDay(day);
        log.info("finish work");
    }

    public void printAll() {
        log.info("print all days");
        dayService.getDays().forEach(day -> log.info(String.valueOf(day)));
        log.info("print all reasons");
        reasonService.getReasons().forEach(reason -> log.info(String.valueOf(reason)));
        log.info("print all operations");
        operationService.getOperations().forEach(operation -> log.info(String.valueOf(operation)));
    }
}
