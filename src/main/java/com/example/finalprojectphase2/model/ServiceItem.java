package com.example.finalprojectphase2.model;

import com.example.finalprojectphase2.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Service_Item", schema = "psn1",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class ServiceItem extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "base_price")
    private Float basePrice;

    @ManyToMany(mappedBy = "expertSkills", fetch = FetchType.EAGER)
    private Set<User> experts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceTypeId;

}

