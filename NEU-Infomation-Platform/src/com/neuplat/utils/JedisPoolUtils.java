package com.neuplat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
	
	private static JedisPool pool = null;
	
	static{
		
		//���������ļ�
		InputStream in = JedisPoolUtils.class.getClassLoader().getResourceAsStream("redis.properties");
		Properties pro = new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//��ó��Ӷ���
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(Integer.parseInt(pro.get("redis.maxIdle").toString()));//������ø���
		poolConfig.setMinIdle(Integer.parseInt(pro.get("redis.minIdle").toString()));//��С���ø���
		poolConfig.setMaxTotal(Integer.parseInt(pro.get("redis.maxTotal").toString()));//���������
		pool = new JedisPool(poolConfig,pro.getProperty("redis.url") , Integer.parseInt(pro.get("redis.port").toString()));
	}

	//���jedis��Դ�ķ���
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	public static void main(String[] args) {
		Jedis jedis = getJedis();
		System.out.println(jedis.get("xxx"));
	}
	
	
	
	
}
