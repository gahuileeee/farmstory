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
@Table(name="carts")

public class Carts {
    @Id
    private int catNo;
    private String userId;
    private int prodNo;
    private int cartProdCount;
    private LocalDateTime catProdDate;
}
