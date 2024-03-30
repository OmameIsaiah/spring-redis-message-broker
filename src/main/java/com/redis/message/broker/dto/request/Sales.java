package com.redis.message.broker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sales implements Serializable {
    private Long id;
    private String saleDate;
    private String product;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", saleDate='" + saleDate + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
