package example;

import example.worker.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            List<Map.Entry<String, String>> reasons = new ArrayList<>();
            reasons.add(new AbstractMap.SimpleEntry<>("name1", "comment1"));
            reasons.add(new AbstractMap.SimpleEntry<>("name2", "comment2"));

            worker.printAll();
            worker.createWorkOperation(name, initiator, reasons);
            worker.printAll();
        };
    }
}
