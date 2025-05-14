package com.test.voucher.service;

import com.test.voucher.dto.VoucherRequest;
import com.test.voucher.dto.VoucherResponse;

import java.util.List;

public interface VoucherService {
    VoucherResponse generateVoucher(VoucherRequest voucherRequest);

    VoucherResponse validateVoucher(VoucherRequest voucherRequest);

    List<VoucherResponse> getValidVouchersByEmail(String email);
}
