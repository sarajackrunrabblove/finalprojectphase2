package com.example.finalprojectphase2.model;

import com.example.finalprojectphase2.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(
        name = "service", schema = "psn1",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class HomeService extends BaseModel {
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

    @ManyToMany(mappedBy = "expertSkills")
    private Set<User> experts;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

