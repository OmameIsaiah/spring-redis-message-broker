package com.redis.message.broker.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.message.broker.publisher.Publisher;
import com.redis.message.broker.subscriber.Receiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Value("${redis.channel.topic}")
    public String channelTopic;

    @Value("${spring.redis.host}")
    public String redisHostName;
    @Value("${spring.redis.port}")
    public Integer redisPortNumber;

    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration();
        configuration.setHostName(redisHostName);
        configuration.setPort(redisPortNumber);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template =
                new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

    /*@Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template =
                new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        //template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }*/


    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(channelTopic);
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new Receiver(objectMapper()));
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }

    @Bean
    public Publisher publisher() {
        return new Publisher(redisTemplate(), topic(), objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
