package com.nhnacademy.account.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(length = 100)
    @Setter
    private String pwd;

    @Column(length = 100)
    @Setter
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Status status; // enum으로

}
