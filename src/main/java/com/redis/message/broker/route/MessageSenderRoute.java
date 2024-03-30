package com.redis.message.broker.route;

import com.redis.message.broker.dto.request.Sales;
import com.redis.message.broker.dto.response.ApiResponse;
import com.redis.message.broker.utils.MessageUtils;
import com.redis.message.broker.dto.request.Product;
import com.redis.message.broker.publisher.Publisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/publish")
@RequiredArgsConstructor
@Slf4j
public class MessageSenderRoute {
    private final Publisher publisher;

    @PostMapping("/product")
    public ResponseEntity publishProduct(@RequestBody Product product) {
        if (Objects.isNull(product)) {
            return ResponseEntity.badRequest().body(new
                    ApiResponse(
                    HttpStatus.BAD_REQUEST.name(),
                    MessageUtils.FAILED,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid request parameters, please check!"));
        }
        String result = publisher.publishProduct(product);
        return ResponseEntity.ok().body(new
                ApiResponse(
                HttpStatus.OK.name(),
                MessageUtils.SUCCESS,
                HttpStatus.OK.value(),
                result));
    }

    @PostMapping("/sales")
    public ResponseEntity publishSales(@RequestBody Sales sales) {
        if (Objects.isNull(sales)) {
            return ResponseEntity.badRequest().body(new
                    ApiResponse(
                    HttpStatus.BAD_REQUEST.name(),
                    MessageUtils.FAILED,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid request parameters, please check!"));
        }
        sales.setTotalPrice((sales.getQuantity() * sales.getUnitPrice()));
        String result = publisher.publishSales(sales);
        return ResponseEntity.ok().body(new
                ApiResponse(
                HttpStatus.OK.name(),
                MessageUtils.SUCCESS,
                HttpStatus.OK.value(),
                result));
    }
}
