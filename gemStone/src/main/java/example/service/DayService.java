package example.service;

import com.gemstone.gbj.*;
import example.client.GemStoneClient;
import example.model.Day;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DayService {

    @Autowired
    private GemStoneClient client;

    public Optional<GbjObject> getLastOpenDay() {
        try {
            client.connect();
            GbjCollection dayStubs = (GbjCollection) client.getSession().doit("Day allDays");
            GbjObject dayStub = dayStubs.detect("[:cust | cust status = 1]");
            client.disconnect();
            // .getObjs()
            return Optional.of(dayStub);
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void closeDay(GbjObject dayStub) {
        try {
            client.connect();
            dayStub.sendMsg("status:", 2);
            client.disconnect();
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
    }

    public void openDay(GbjObject dayStub) {
        try {
            client.connect();
            dayStub.sendMsg("status:", 1);
            client.disconnect();
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
    }

    public Optional<GbjObject> createDay() {
        try {
            client.connect();
            GbjObject dayClass = client.getSession().objectNamed("Day");
            GbjObject dayStub = dayClass.sendMsg("status:", 1);
            GbjCollection allDays = (GbjCollection) dayClass.sendMsg("allDays");
            allDays.add(dayStub);
            client.disconnect();
            return Optional.of(dayStub);
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Day> getDays() {
        List<Day> days = new ArrayList<>();
        try {
            client.connect();
            GbjCollection allDayStubs = (GbjCollection) client.getSession().doit("Day allDays");
            for (Enumeration e = allDayStubs.elements(); e.hasMoreElements(); ) {
                Day dayStub = (Day) e.nextElement();
                days.add(dayStub);
            }
            client.disconnect();
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
        return days;
    }
}
