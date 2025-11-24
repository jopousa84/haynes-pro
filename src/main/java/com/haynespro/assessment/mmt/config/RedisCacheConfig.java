package com.haynespro.assessment.mmt.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Value("${cache.ttl.makes:3600}")
    private long makesTtlSeconds;

    @Value("${cache.ttl.modelsByMake:3600}")
    private long modelsByMakeTtlSeconds;

    @Value("${cache.ttl.typesByModel:3600}")
    private long typesByModelTtlSeconds;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Use defaults from spring.redis.* properties (Lettuce)
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(serializer);

        RedisCacheConfiguration makesConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair)
                .entryTtl(Duration.ofSeconds(makesTtlSeconds));

        RedisCacheConfiguration modelsByMakeConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair)
                .entryTtl(Duration.ofSeconds(modelsByMakeTtlSeconds));

        RedisCacheConfiguration typesByModelConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair)
                .entryTtl(Duration.ofSeconds(typesByModelTtlSeconds));

        Map<String, RedisCacheConfiguration> configs = new HashMap<>();
        configs.put("makes", makesConfig);
        configs.put("modelsByMake", modelsByMakeConfig);
        configs.put("typesByModel", typesByModelConfig);

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(makesConfig)
                .withInitialCacheConfigurations(configs)
                .transactionAware()
                .build();
    }
}
