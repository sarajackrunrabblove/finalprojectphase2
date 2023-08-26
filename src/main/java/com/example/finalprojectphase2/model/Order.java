package com.example.finalprojectphase2.model;


import com.example.finalprojectphase2.model.base.BaseModel;
import com.example.finalprojectphase2.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @OneToOne(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private ServiceItem homeService;

    @OneToOne(cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;

    @OneToMany(mappedBy = "order", fetch=FetchType.EAGER)
    private List<Offer> offers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "final_offer_id", referencedColumnName = "id")
    private Offer finalOffer;

}
