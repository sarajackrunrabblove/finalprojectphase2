package com.example.finalprojectphase2.payload;

import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private UserRole role;
    private ExpertStatus status;
    private String registrationDate;
    private Float credit;
    private Integer rate;
    private Set<Long> expertSkills;
    private Long creatorUserId;
    private Long modifierUserId;

}
