package kr.co.farmstory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Entity
@Table(name="orderItems")

public class OrderItems {
    @Id
    private int itemNo;
    private int orderNo;
    private int prodNo;
    private int itemPrice;
    private int itemDiscount;
    private int itemCount;
}
