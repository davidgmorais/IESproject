package ies.project.toSeeOrNot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author David
 * @date 2021/1/9 17:19
 */
@Configuration
public class WeConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedHeaders("*")
                .allowedMethods("GET","POST", "PUT", "DELETE")
                .exposedHeaders("Authentication", "registerToken")
                .allowCredentials(true);
    }
}
