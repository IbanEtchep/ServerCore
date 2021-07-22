package fr.iban.common.data.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisAccess {
	
	
	private static RedisAccess instance;
	private RedissonClient redissonClient;
	private RedisCredentials credentials;
	
	
	public RedisAccess(RedisCredentials credentials) {
		instance = this;
		this.credentials = credentials;
		this.redissonClient = initRedisson(credentials);
	}
	
	public static void init(RedisCredentials credentials) {
		new RedisAccess(credentials);
	}
	
	public static void close() {
		getInstance().getRedissonClient().shutdown();
	}
	
	public RedissonClient initRedisson(RedisCredentials credentials) {
		Config config = new Config();
		config.setCodec(new JsonJacksonCodec());
		config.setUseLinuxNativeEpoll(true);
		config.setThreads(8);
		config.setNettyThreads(8);
		config.useSingleServer()
			.setAddress(credentials.toRedisURL())
			.setDatabase(3)
			.setPassword(credentials.getPassword())
			.setClientName(credentials.getClientName());
		
		return Redisson.create(config);
	}
	
	public RedissonClient getRedissonClient() {
		if(redissonClient == null || redissonClient.isShutdown()) {
			System.out.println("REDIS OFF - TENTATIVE DE RECONNEXION");
			redissonClient = initRedisson(credentials);
		}
		return redissonClient;
	}

	public static RedisAccess getInstance() {
		return instance;
	}

}
