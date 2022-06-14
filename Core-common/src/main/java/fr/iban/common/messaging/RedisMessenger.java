package fr.iban.common.messaging;

import fr.iban.common.data.redis.RedisAccess;
import org.redisson.api.RTopic;
import org.redisson.api.listener.MessageListener;

public class RedisMessenger extends AbstractMessenger implements MessageListener<Message> {

    private final RTopic topic;

    public RedisMessenger() {
        topic = RedisAccess.getInstance().getRedissonClient().getTopic("CoreMessage");
        topic.addListener(Message.class, this);
    }

    @Override
    public void sendMessage(Message message) {
        topic.publishAsync(message);
    }


    @Override
    public void onMessage(CharSequence channel, Message msg) {
        messageConsumer.accept(msg);
    }
}
