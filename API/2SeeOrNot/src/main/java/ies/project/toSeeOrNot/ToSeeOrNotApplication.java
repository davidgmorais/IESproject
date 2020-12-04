package ies.project.toSeeOrNot;
import ies.project.toSeeOrNot.utils.MyBeanUtils;
import ies.project.toSeeOrNot.utils.JWTUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ JWTUtils.class })
public class ToSeeOrNotApplication {

    public static void main(String[] args) {
        MyBeanUtils.applicationContext = SpringApplication.run(ToSeeOrNotApplication.class, args);
    }

}
