package com.test.voucher.service;

import com.test.voucher.dto.VoucherRequest;
import com.test.voucher.dto.VoucherResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements  VoucherService{

    @Override
    public VoucherResponse generateVoucher(VoucherRequest voucherRequest) {
        return null;
    }

    @Override
    public VoucherResponse validateVoucher(VoucherRequest voucherRequest) {
        return null;
    }

    @Override
    public List<VoucherResponse> getValidVouchersByEmail(String email) {
        return List.of();
    }
}
