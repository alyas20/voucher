package com.test.voucher.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RECIPIENT01")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 12)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 20, unique = true)
    private String email;
}
