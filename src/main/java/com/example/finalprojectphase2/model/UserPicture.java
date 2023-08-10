package com.example.finalprojectphase2.model;

import com.example.finalprojectphase2.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "user_picture", schema = "psn1")
public class UserPicture extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @OneToOne(mappedBy = "picture")
    private User user;

}
