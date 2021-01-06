package example.service;

import com.google.common.collect.Lists;
import example.model.Day;
import example.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class DayService {

    @Autowired
    private DayRepository repository;

    public Optional<Day> getLastOpenDay() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(day -> day.getStatus() == 1).findFirst();
    }

    public void closeDay(Long id) {
        Optional<Day> day = repository.findById(id);
        day.ifPresent(value -> {
            value.setStatus(2);
            repository.save(day.get());
        });
    }

    public void openDay(Long id) {
        Optional<Day> day = repository.findById(id);
        day.ifPresent(value -> {
            value.setStatus(1);
            repository.save(day.get());
        });
    }

    public Long createDay() {
        Day day = new Day();
        return repository.save(day).getId();
    }

    public List<Day> getDays() {
        return Lists.newArrayList(repository.findAll());
    }
}
