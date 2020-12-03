package ies.project.toSeeOrNot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Wei
 * @date 2020/12/3 16:16
 */
@Slf4j
public class BeanUtils {
    public static ConfigurableApplicationContext applicationContext;

    /**
     * find bean
     * @param c class
     * @param <T> type of bean
     * @return bean
     */
    public static <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }
}
