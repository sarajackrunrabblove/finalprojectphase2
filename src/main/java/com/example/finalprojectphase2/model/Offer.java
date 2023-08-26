package com.example.finalprojectphase2.model;

import com.example.finalprojectphase2.model.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "offer", schema = "psn1")
public class Offer extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "offered_price")
    private Float offeredPrice; // قیمت پیشنهادی متخصص

    @Column(name = "offered_starting_time")
    private LocalDateTime offeredStartingTime;

    @Column(name = "duration")
    private Duration duration;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "expert_id", referencedColumnName = "id")
    private User expert;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "rate")
    private Integer expertRate = 0;

    @Column(name = "review_description")
    private String reviewDescription;       //نظر مشتری درباره متخصص

}
