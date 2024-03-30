package com.redis.message.broker.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.message.broker.dto.request.Product;
import com.redis.message.broker.dto.request.QueueRequest;
import com.redis.message.broker.dto.request.QueueTypes;
import com.redis.message.broker.dto.request.Sales;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Slf4j
public class Publisher {
    private final RedisTemplate template;
    private final ChannelTopic topic;
    private final ObjectMapper objectMapper;

    public String publishProduct(@RequestBody Product product) {
        QueueRequest request = QueueRequest.builder()
                .type(QueueTypes.PRODUCT)
                .data(product)
                .build();
        String content = "";
        try {
            content = objectMapper.writeValueAsString(request);
            log.info("######################### Event sent: {}", content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error writing content: {}" + e.getMessage());
        }
        template.convertAndSend(topic.getTopic(), content);
        return "Event published!";
    }

    public String publishSales(@RequestBody Sales sales) {
        QueueRequest request = QueueRequest.builder()
                .type(QueueTypes.SALES)
                .data(sales)
                .build();
        String content = "";
        try {
            content = objectMapper.writeValueAsString(request);
            log.info("######################### Event sent: {}", content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error writing content: {}" + e.getMessage());
        }
        template.convertAndSend(topic.getTopic(), content);
        return "Event published!";
    }
}
