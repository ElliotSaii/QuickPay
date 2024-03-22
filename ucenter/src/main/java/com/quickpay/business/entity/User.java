package com.quickpay.business.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^.{16}$", message = "exceeded length")
    private String name;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$" , message = "not formatted email")
    private String email;

    @NotNull
    @Pattern(regexp = "^.{200}$", message = "exceeded length")
    private String address;

    @NotNull
    @Pattern(regexp = "^.{250}$", message = "exceeded length")
    private String contactDetails;

    @Column(name = "created_time")
    @JsonFormat(timezone = "GMT+6:30",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

}
