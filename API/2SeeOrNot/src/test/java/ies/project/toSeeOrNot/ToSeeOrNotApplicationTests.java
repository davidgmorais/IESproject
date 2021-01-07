package ies.project.toSeeOrNot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.persistence.Table;
import java.io.PrintStream;

@SpringBootTest
class ToSeeOrNotApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void t(){
        System.out.println(redisTemplate);
    }
}
