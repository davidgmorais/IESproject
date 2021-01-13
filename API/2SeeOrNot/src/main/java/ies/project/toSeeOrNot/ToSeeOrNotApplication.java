package ies.project.toSeeOrNot;
import ies.project.toSeeOrNot.utils.JWTUtils;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties({ JWTUtils.class })
@EnableRabbit
@EnableCaching
@EnableSwagger2
public class ToSeeOrNotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToSeeOrNotApplication.class, args);
    }
}
