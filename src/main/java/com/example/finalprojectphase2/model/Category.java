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
@ToString(callSuper = true)
@Table(
        name = "category", schema = "psn1",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class Category extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", fetch=FetchType.LAZY)
    private Set<HomeService> homeService;

}
