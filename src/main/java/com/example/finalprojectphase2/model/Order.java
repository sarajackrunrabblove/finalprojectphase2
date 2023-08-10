package com.example.finalprojectphase2.model;


import com.example.finalprojectphase2.model.base.BaseModel;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "order_", schema = "psn1")
public class Order extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "customer_price")
    private Float customerPrice; // قیمت درخواستی کاربر

    @Column(name = "order_time")
    @CreationTimestamp
    private LocalDateTime orderTime;

    @Column(name = "starting_time")
    private LocalDateTime startingTime;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.WAITING_FOR_EXPERT_OFFER;

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private HomeService homeService;

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;

    @OneToMany(mappedBy = "order", fetch=FetchType.LAZY)
    private List<Offer> offers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "final_offer_id", referencedColumnName = "id")
    private Offer finalOffer;

}
