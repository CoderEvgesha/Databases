package example.service;

import com.google.common.collect.Lists;
import example.model.Operation;
import example.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperationService {

    @Autowired
    private OperationRepository repository;

    public List<Operation> getOperations() {
        return Lists.newArrayList(repository.findAll().iterator());
    }

    public Long createOperation(Operation operation) {
        return repository.save(operation).getId();
    }
}
