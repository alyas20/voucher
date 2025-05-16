package com.test.voucher.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherRequest {

    private String specialOffer;
    private String expDate;
    private List<String> emails;
}
