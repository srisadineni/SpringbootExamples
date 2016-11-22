package etl.app;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EtlApplication extends CommandLineJobRunner{

    public static void main(String[] args) {
        SpringApplication.run(EtlApplication.class, args);
    }
}
