package kr.co.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int no;

    public int parent;
    public int comment;
    public String cate;
    public String title;
    public String content;
    public int file;
    public int hit;
    public String writer;

    public String regip;

    @CreationTimestamp
    public LocalDateTime rdate;

    @Transient
    private String nick;
}