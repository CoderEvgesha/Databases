package example.service;

import com.gemstone.gbj.*;
import example.client.GemStoneClient;
import example.model.Reason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Component
public class ReasonService {

    @Autowired
    private GemStoneClient client;

    public Optional<GbjObject> createReason(Object... args) {
        try {
            client.connect();
            GbjObject reasonClass = client.getSession().objectNamed("Reason");
            GbjObject reasonStub = reasonClass.perform("name:comment:",
                    args, args.length);
            GbjCollection allReasons = (GbjCollection) reasonClass.sendMsg("allReasons");
            allReasons.add(reasonStub);
            client.disconnect();
            return Optional.of(reasonStub);
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Reason> getReasons() {
        List<Reason> reasons = new ArrayList<>();
        try {
            client.connect();
            GbjCollection allReasonStubs = (GbjCollection) client.getSession().doit("Reason allReasons");
            for (Enumeration e = allReasonStubs.elements(); e.hasMoreElements(); ) {
                Reason reasonStub = (Reason) e.nextElement();
                reasons.add(reasonStub);
            }
            client.disconnect();
        } catch (GbjEventException | GbjException e) {
            log.error("GemStone exception error: " + e.getMessage());
        }
        return reasons;
    }
}
