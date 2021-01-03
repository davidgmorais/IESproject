package ies.project.toSeeOrNot;
import ies.project.toSeeOrNot.utils.JWTUtils;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ JWTUtils.class })
@EnableRabbit
public class ToSeeOrNotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToSeeOrNotApplication.class, args);
    }

}
