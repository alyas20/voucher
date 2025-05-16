package com.test.voucher.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherResponse {

    private boolean isValid;
    private Integer fixedPercentDiscount;
    private String message;
    private String voucherCode;
    private String specialOffer;
}
