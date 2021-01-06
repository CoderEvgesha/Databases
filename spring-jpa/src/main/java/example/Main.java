package example;

import example.model.Reason;
import example.worker.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public CommandLineRunner application(Worker worker) {
        return (args) -> {
            String name = "name";
            String initiator = "initiator";
            Reason reason1 = new Reason().setName("name1").setComment("comment1");
            Reason reason2 = new Reason().setName("name2").setComment("comment2");
            List<Reason> reasons = new ArrayList<>();
            reasons.add(reason1);
            reasons.add(reason2);

            worker.printAll();
            worker.createWorkOperation(name, initiator, reasons);
            worker.printAll();
        };
    }
}
