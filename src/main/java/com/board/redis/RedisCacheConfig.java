package com.board.redis;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@Log4j
public class RedisCacheConfig extends CachingConfigurerSupport {

	/**
	 * redis 2.0 버전 이후부터 RedisStandaloneConfiguration 클래스로 설정해야 된다.
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("127.0.0.1");
		redisStandaloneConfiguration.setPort(6379);
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpccb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) 
				JedisClientConfiguration.builder();
		jpccb.poolConfig(jedisPoolConfig());
		JedisClientConfiguration jedisClientConfiguration = jpccb.build();
		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
	}

	private JedisPoolConfig jedisPoolConfig() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(300);
		poolConfig.setMaxTotal(2000);
		poolConfig.setMaxWaitMillis(1000);
		poolConfig.setTestOnBorrow(true);
		return poolConfig;
	}

	/**
	 * 스프링에서 제공하는 Serializer를 CacheManager에 등록.
	 * 이렇게 하면 캐싱되는 모델마다 Serializable을 implements할 필요가 없다.
	 */
	@Bean
	public CacheManager cacheManager(final RedisConnectionFactory factory) {
		Duration expiration = Duration.ofSeconds(300);
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(expiration);
		
		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(factory).cacheDefaults(redisCacheConfiguration).build();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new GenericToStringSerializer<Object>(Object.class));
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {

			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName());
				sb.append(method.getName());
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				log.debug("KeyGenerator: " + sb);
				return sb.toString();
			}
		};
	}
}
