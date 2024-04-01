package kr.co.farmstory.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Entity
@Table(name="Orders")

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;
    private String userId;
    private int orderTotalPrice;
    private String orderAddr;
    private int itemDiscount;
    private LocalDateTime orderDate;
}
