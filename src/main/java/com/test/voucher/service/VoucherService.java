package com.test.voucher.service;

import com.test.voucher.dto.VoucherRequest;
import com.test.voucher.dto.VoucherResponse;

import java.util.List;

public interface VoucherService {
    void generateVoucher(VoucherRequest voucherRequest);

    VoucherResponse validateVoucher(String voucherCode,String email);

    List<VoucherResponse> getValidVouchersByEmail(String email);
}
