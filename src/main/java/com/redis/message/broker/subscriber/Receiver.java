package com.redis.message.broker.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.message.broker.dto.request.Product;
import com.redis.message.broker.dto.request.QueueRequest;
import com.redis.message.broker.dto.request.QueueTypes;
import com.redis.message.broker.dto.request.Sales;
import com.redis.message.broker.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class Receiver implements MessageListener {

    @Value("${redis.channel.topic}")
    public String channelTopic;
    //Logger log = LoggerFactory.getLogger(Receiver.class);
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("######################### Event Received: {}", message);
        String body = decodeByte(message.getBody());
        String channel = decodeByte(message.getChannel());
        log.info("####################### BODY: {}", body);
        log.info("####################### CHANNEL: {}", channel);
        if (Objects.nonNull(body) && Objects.nonNull(channel)) {
            if (channel.equalsIgnoreCase("sample-topic")) {
                try {
                    QueueRequest request = Utils.getGson().fromJson(body, QueueRequest.class);
                    log.info("VALUE AFTER CONVERSION: {}", request);
                    log.info("MESSAGE TYPE: {}", request.getType());
                    if (QueueTypes.PRODUCT.equalsIgnoreCase(request.getType())) {
                        String data = objectMapper.writeValueAsString(request.getData());
                        Product product = Utils.getGson().fromJson(data, Product.class);
                        log.info("**************Product Information Received: {}", product.toString());
                    } else if (QueueTypes.SALES.equalsIgnoreCase(request.getType())) {
                        String data = objectMapper.writeValueAsString(request.getData());
                        Sales sales = Utils.getGson().fromJson(data, Sales.class);
                        log.info("**************Sales Information Received: {}", sales.toString());
                    } else {
                        log.info("DEFAULT**************Type Not Found: {}", request.getData().toString());
                    }
                } catch (Exception e) {
                    log.error("Error converting data", e.getMessage());
                    e.printStackTrace();
                }
            } else {
                log.warn("Oops! No channel found for message sent!");
            }
        } else {
            log.warn("Oops! Invalid message received, could not decode the message!");
        }


    }

    public String decodeByte(byte[] byteArray) {
        // Decode byte array to string using UTF-8 character set
        return new String(byteArray, StandardCharsets.UTF_8);
    }

}
