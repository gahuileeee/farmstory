package kr.co.farmstory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Entity
@Table(name="points")

public class Points {
    @Id
    private int pointNo;
    private String userId;
    private int point;
    private String pointDesc;
    @CreationTimestamp
    private LocalDateTime pointDate;
}
