package ies.project.toSeeOrNot.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Wei
 * @date 2021/1/9 17:16
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void storeSet(String key, Set set) {
        redisTemplate.opsForSet().add(key, set, 60 * 10, TimeUnit.SECONDS);
    }

    public void del(String ... key){
        if(key != null && key.length > 0){
            if(key.length == 1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * get cache
     * @param key
     * @return
     */
    public Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * add data to cache
     * @param key key
     * @param value data
     * @return true if success
     */
    public boolean add(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value, 60 * 10, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get datas with index between start and end
     * return entire list if start 0 and end -1
     * @param key key
     * @return list
     */
    public Set getSet(String key){
        try {
                return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
