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
@ToString(onlyExplicitlyIncluded = true)
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 12)
    private Long id;

    @ToString.Include
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ToString.Include
    @Column(name = "email", nullable = false, length = 20)
    private String email;
}
