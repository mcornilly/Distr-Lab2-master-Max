
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class BankingServerSpringApp {
    public static void main(String[]  args) {
        SpringApplication springApp = new SpringApplication(BankingServerSpringApp.class); // new spring app we can run
        Properties properties = new Properties(); //property list for springApp
        properties.put("server.port", "8080"); // use server port 8080
        springApp.setDefaultProperties(properties);
        springApp.run(args); // Run the springApp (naming server)
    }
}
