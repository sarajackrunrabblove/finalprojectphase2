package com.example.finalprojectphase2.model;

import com.example.finalprojectphase2.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Service_Type", schema = "psn1",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class ServiceType extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "serviceType", fetch=FetchType.LAZY)
    private Set<ServiceItem> homeService;

}
