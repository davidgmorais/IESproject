package ies.project.toSeeOrNot.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/5 12:59
 */
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        // used in serialization
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();

        //redisCache
        RedisCacheConfiguration config=RedisCacheConfiguration.defaultCacheConfig();

        config = config.entryTtl(Duration.ofDays(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                .disableCachingNullValues();

        Set<String> cacheNames=new HashSet<>();
        cacheNames.add("user");
        cacheNames.add("cinema");
        cacheNames.add("room");
        cacheNames.add("premier");
        cacheNames.add("schedule");
        cacheNames.add("notification");
        cacheNames.add("seat");
        cacheNames.add("comment");
        cacheNames.add("film");
        cacheNames.add("actor");

        Map<String,RedisCacheConfiguration> configMap=new HashMap<>();
        RedisCacheConfiguration finalConfig = config;

        cacheNames.forEach(cacheName -> {
            configMap.put(cacheName, finalConfig);
        });


        return RedisCacheManager.builder(factory)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
    }
}
