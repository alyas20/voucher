package com.test.voucher.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "VOUCHER01")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher {

    @Id
    @Column(name = "generated_code", nullable = false, length = 10, unique = true)
    private String generatedCode;

    @Temporal(TemporalType.DATE)
    @Column(name = "exp_date")
    private Date expDate;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Temporal(TemporalType.DATE)
    @Column(name = "usage_date")
    private Date usageDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "special_offer_id", nullable = false)
    private SpecialOffer specialOffer;
}
