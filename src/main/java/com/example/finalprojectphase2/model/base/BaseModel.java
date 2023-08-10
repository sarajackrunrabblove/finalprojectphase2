package com.example.finalprojectphase2.model.base;

import com.example.finalprojectphase2.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseModel {

    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "modify_date")
    @UpdateTimestamp
    private LocalDateTime modifyDate;

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", referencedColumnName = "id")
    private User creatorUser;

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "modifier_user_id", referencedColumnName = "id")
    private User modifierUser;

    @Column(name = "is_Active")
    private Boolean isActive;

}
