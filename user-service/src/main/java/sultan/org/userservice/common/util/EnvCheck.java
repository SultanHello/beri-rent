package sultan.org.userservice.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvCheck implements CommandLineRunner {
    @Value("${DATASOURCE_URL}")
    private String url;

    @Override
    public void run(String... args) {
        System.out.println("DATASOURCE_URL = " + url);
    }
}