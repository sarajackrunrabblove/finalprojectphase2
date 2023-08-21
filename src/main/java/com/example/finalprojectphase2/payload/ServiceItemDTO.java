package com.example.finalprojectphase2.payload;

import com.example.finalprojectphase2.model.ServiceType;
import com.example.finalprojectphase2.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemDTO {

    private String title;
    private Float basePrice;
    private String description;
    private ServiceType category;
    private User creatorUser;
}
