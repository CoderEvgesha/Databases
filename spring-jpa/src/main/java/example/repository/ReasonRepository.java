package example.repository;

import example.model.Reason;
import org.springframework.data.repository.CrudRepository;

public interface ReasonRepository extends CrudRepository<Reason, Long> {
}
