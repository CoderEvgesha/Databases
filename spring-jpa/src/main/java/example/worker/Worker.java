package example.worker;

import example.model.Day;
import example.model.Operation;
import example.model.Reason;
import example.service.DayService;
import example.service.OperationService;
import example.service.ReasonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    public void createWorkOperation(String name, String initiator, List<Reason> reasons) {
        log.info("start work");
        Long dayId;
        Optional<Day> dayOptional = dayService.getLastOpenDay();
        if (dayOptional.isEmpty()) {
            dayId = dayService.createDay();
            dayService.openDay(dayId);
        } else {
            dayId = dayOptional.get().getId();
        }
        List<Long> reasonsId = new ArrayList<>();
        reasons.forEach(reason -> reasonsId.add(reasonService.createReason(reason)));
        Operation operation = new Operation()
                .setName(name)
                .setInitiator(initiator)
                .setReasons(reasonsId);
        operationService.createOperation(operation);
        dayService.closeDay(dayId);
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
