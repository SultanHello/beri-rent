package sultan.org.userservice;

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sultan.org.userservice.common.util.EnvCheck;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(UserServiceApplication.class, args);
		EnvCheck envCheck = context.getBean(EnvCheck.class);
		envCheck.run();
	}

}
