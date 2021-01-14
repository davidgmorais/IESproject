package ies.project.toSeeOrNot;
import ies.project.toSeeOrNot.component.RedisUtils;
import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class ToSeeOrNotApplicationTests {

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void t(){
        PageDTO<UserDTO> pageDTO= new PageDTO<>();
        pageDTO.setTotalElements(10);
        pageDTO.setTotalPages(12);
        Set<UserDTO> objects = new HashSet<>();
        objects.add(new UserDTO());
        pageDTO.setData(objects);
        redisUtils.add("s", pageDTO);
        Object s = redisUtils.get("s");
        System.out.println(s);
    }
}
