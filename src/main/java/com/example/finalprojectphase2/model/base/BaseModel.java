package com.example.finalprojectphase2.model.base;

import com.example.finalprojectphase2.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
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

    private Long creatorUser;

    private Long modifierUser;

    @Column(name = "is_Active")
    private Boolean isActive;

    public void setCreatorUser(User creatorUser) {
        this.creatorUser = creatorUser.getId();
    }

    public void setCreatorUser(Long creatorUserId) {
        this.creatorUser = creatorUserId;
    }

    public void setModifierUser(User modifierUser) {
        this.modifierUser = modifierUser.getId();
    }

    public void setModifierUser(Long modifierUserId) {
        this.modifierUser = modifierUserId;
    }

}
