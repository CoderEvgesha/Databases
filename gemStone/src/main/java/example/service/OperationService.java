package example.service;

import com.gemstone.gbj.*;
import example.client.GemStoneClient;
import example.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Component
public class OperationService {

    @Autowired
    private GemStoneClient client;

    public Optional<GbjObject> createOperation(Object... args) {
        try {
            client.connect();
            GbjObject operationClass = client.getSession().objectNamed("Operation");
            GbjObject operationStub = operationClass.perform("name:initiator:reasons:",
                    args, args.length);
            GbjCollection allOperations = (GbjCollection) operationClass.sendMsg("allOperations");
            allOperations.add(operationStub);
            client.disconnect();
            return Optional.of(operationStub);
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Operation> getOperations() {
        List<Operation> operations = new ArrayList<>();
        try {
            client.connect();
            GbjCollection allOperationStubs = (GbjCollection) client.getSession().doit("Operation allOperations");
            for (Enumeration e = allOperationStubs.elements(); e.hasMoreElements(); ) {
                Operation operationStub = (Operation) e.nextElement();
                operations.add(operationStub);
            }
            client.disconnect();
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
        return operations;
    }
}
