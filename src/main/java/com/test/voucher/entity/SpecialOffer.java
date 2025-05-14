package com.test.voucher.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SPECIAL_OFFER01")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 12)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "fix_percent_discount", nullable = false, length = 2)
    private Integer fixPercentDisc;
}
