package com.board.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * Redis+Jedis 동작 테스트
 *
 */
public class RedisCacheConfigTest {

	private static final Jedis jedis = new Jedis("127.0.0.1", 6379);

	@Test
	public void 기본_테스트() {
		jedis.set("welcome", "hello");
		String value = jedis.get("welcome");
		assertThat(value, is("hello"));
	}

	@Test
	public void 스레드_세이프_여부_테스트() {
		System.out.println("Service is running: "+jedis.ping());
        for (int i = 0; i <2; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int j = 0; j <10; j++) {
                    jedis.set("a" + finalI, String.valueOf(finalI));
                    System.err.println("a" + finalI + "=" + jedis.get("a" + finalI));
                }
            }).start();
        }
	}

}
