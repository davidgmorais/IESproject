package ies.project.toSeeOrNot;
import ies.project.toSeeOrNot.utils.MyBeanUtils;
import ies.project.toSeeOrNot.utils.JWTUtils;
import ies.project.toSeeOrNot.utils.consuming.DynamicDataScript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableConfigurationProperties({ JWTUtils.class })
public class ToSeeOrNotApplication {

    public static void main(String[] args) {
        MyBeanUtils.applicationContext = SpringApplication.run(ToSeeOrNotApplication.class, args);
        /*try{
            DynamicDataScript script = new DynamicDataScript();
            script.run();
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }
}
