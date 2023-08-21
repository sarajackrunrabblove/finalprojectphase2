package com.example.finalprojectphase2.payload;

import com.example.finalprojectphase2.model.enums.ExpertStatus;
import com.example.finalprojectphase2.model.enums.UserRole;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private ExpertStatus status;
    private String registrationDate;
    private Float credit;
    private Set<Long> expertSkills;
    private Long creatorUserId;
    private Long modifierUserId;

}
