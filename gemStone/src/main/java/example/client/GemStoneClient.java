package example.client;

import com.gemstone.gbj.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GemStoneClient {

    private final GbjSession session;

    public GemStoneClient() {
        this.session = new GbjSession(getParameters());
    }

    public void connect() {
        session.connect();
    }

    public void disconnect() {
        session.close();
    }

    private GbjParameters getParameters() {
        GbjParameters params = new GbjParameters();
        params.userName = "admin";
        params.password = "12345";
        params.serverName = "gs64stone";
        params.gemnetName = "gemnetobject";
        params.transactionMode = GbjParameters.ManualTransactions;
        return params;
    }
}
