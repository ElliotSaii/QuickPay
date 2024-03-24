package com.quickpay.business.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String latitude;
    private String longitude;

    @ManyToOne(targetEntity= User.class)
    @JoinColumn(name = "user_id")
    private User user;

    private Date createdAt;
    private Date updatedAt;
}
