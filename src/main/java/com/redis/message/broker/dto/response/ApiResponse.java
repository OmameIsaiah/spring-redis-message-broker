package com.redis.message.broker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ApiResponse implements Serializable {
    private String status;
    private String message;
    private Integer code;
    private Object data;
}
