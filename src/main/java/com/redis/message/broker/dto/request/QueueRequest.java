package com.redis.message.broker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueueRequest implements Serializable {
    private String type;
    private Object data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QueueRequest{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
