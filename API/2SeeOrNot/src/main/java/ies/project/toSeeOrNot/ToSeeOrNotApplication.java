package ies.project.toSeeOrNot;
import ies.project.toSeeOrNot.utils.JWTUtils;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties({ JWTUtils.class })
@EnableRabbit
@EnableCaching
public class ToSeeOrNotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToSeeOrNotApplication.class, args);
    }

}
