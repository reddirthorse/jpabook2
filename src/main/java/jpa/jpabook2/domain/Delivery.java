package jpa.jpabook2.domain;

import javax.persistence.*;

@Entity
public class Delivery {
    @Id
    @Column(name = "delivery_id")
    @GeneratedValue
    private Long id;

    @OneToOne
    private Order order;

    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
