package example.repository;

import example.model.Day;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface DayRepository extends CrudRepository<Day, Long> {
}
