package com.example.finalprojectphase2.payload;

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
    private Long categoryId;
//    private User creatorUser;
}
