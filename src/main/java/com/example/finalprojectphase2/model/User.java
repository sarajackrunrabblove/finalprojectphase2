package com.example.finalprojectphase2.model;

import com.example.finalprojectphase2.model.base.BaseModel;
import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "psn1")
public class User extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Email(message = "Invalid Email. Please enter proper Email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$",
            message = "Password must be at least 8 characters long and contain at least one letter and one digit and one Capital Letter"
    )
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ExpertStatus status = ExpertStatus.NOT_APPROVED;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.CUSTOMER;

    @Column(name = "registration_date")
    @CreationTimestamp
    private LocalDateTime registrationDate;

    @Column(name = "credit")
    private Float credit = 0.0F; // اعتبار

    @Column(name = "is_Active")
    private Boolean isActive = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_service_item",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "service_item_id"))
    private Set<ServiceItem> expertSkills = new HashSet<>();

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @Column(name = "image_mime_type")
    private String imageMimeType;
}
