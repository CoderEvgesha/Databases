package example.service;

import com.google.common.collect.Lists;
import example.model.Reason;
import example.repository.ReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReasonService {
    @Autowired
    private ReasonRepository repository;

    public Long createReason(Reason reason) {
        return repository.save(reason).getId();
    }

    public List<Reason> getReasons() {
        return Lists.newArrayList(repository.findAll().iterator());
    }
}
