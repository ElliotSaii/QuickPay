package com.quickpay.business.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;

    @OneToMany(targetEntity = Address.class ,mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Address> addresses;

    private Date createdAt;
    private Date lastLogin;

}
