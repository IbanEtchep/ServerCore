package fr.iban.common.messaging;

import com.google.gson.Gson;
import fr.iban.common.data.redis.RedisCredentials;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisMessenger extends AbstractMessenger {

    private static final String CHANNEL = "CoreChannel";
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ExecutorService subscribeExecutor = Executors.newSingleThreadExecutor();
    private final RedisCredentials redisCredentials;
    private JedisPool jedisPool;
    private Subscription sub;
    private boolean closing = false;
    private final Gson gson = new Gson();


    public RedisMessenger(RedisCredentials redisCredentials) {
        this.redisCredentials = redisCredentials;
    }

    @Override
    public void init() {
        this.jedisPool = new JedisPool(redisCredentials.toRedisURL());
        this.sub = new Subscription();
        subscribeExecutor.execute(this.sub);
        System.out.println("Redis pubsub connection established");
    }

    @Override
    public void close() {
        this.closing = true;
        this.sub.unsubscribe();
        this.jedisPool.destroy();
    }

    @Override
    public void sendMessage(Message message) {
        executor.execute(() -> {
            long now = System.nanoTime();
            try (Jedis jedis = this.jedisPool.getResource()) {
                jedis.publish(CHANNEL, gson.toJson(message));
                if (debugMode) {
                    System.out.println("OUT : " + "(" + message.getChannel() + ") " + message.getMessage() + " envoyé à " + System.currentTimeMillis() + " en " + (System.nanoTime() - now) / 1000000.0 + "ms");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private class Subscription extends JedisPubSub implements Runnable {

        @Override
        public void run() {
            boolean first = true;
            while (!RedisMessenger.this.closing && !Thread.interrupted() && !RedisMessenger.this.jedisPool.isClosed()) {
                try (Jedis jedis = RedisMessenger.this.jedisPool.getResource()) {
                    if (first) {
                        first = false;
                    } else {
                        System.out.println("Redis pubsub connection re-established");
                    }

                    jedis.subscribe(this, CHANNEL); // blocking call
                } catch (Exception e) {
                    if (RedisMessenger.this.closing) {
                        return;
                    }


                    System.out.println("Redis pubsub connection dropped, trying to re-open the connection");
                    e.printStackTrace();
                    try {
                        unsubscribe();
                    } catch (Exception ignored) {

                    }

                    // Sleep for 5 seconds to prevent massive spam in console
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        @Override
        public void onMessage(String channel, String msg) {
            if (!channel.equals(CHANNEL)) {
                return;
            }

            RedisMessenger.this.executor.execute(() -> {
                long now = System.nanoTime();
                Message message = gson.fromJson(msg, Message.class);
                if (messageConsumer != null) {
                    messageConsumer.accept(message);
                    if (debugMode) {
                        System.out.println("IN : " + "(" + message.getChannel() + ") " + message.getMessage() + " lu à " + System.currentTimeMillis() + " en " + (System.nanoTime() - now) / 1000000.0 + "ms");
                    }
                }
            });
        }
    }
}
