package com.test.voucher.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "VOUCHER_CODE01")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherCode {

    @Id
    @Column(name = "generated_code", nullable = false, length = 10, unique = true)
    private String generatedCode;

    @Column(name = "exp_date")
    private Date expDate;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "usage_date")
    private Date usageDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_offer_id")
    private SpecialOffer specialOffer;

    @PrePersist
    public void generateCode() {
        // Generate a unique random code with at least 8 characters
        String randomCode = UUID.randomUUID().toString().substring(0, 8); // 8 chars from UUID
        this.generatedCode = randomCode;
    }
}
