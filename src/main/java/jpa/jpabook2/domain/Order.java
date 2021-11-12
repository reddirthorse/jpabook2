package jpa.jpabook2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
