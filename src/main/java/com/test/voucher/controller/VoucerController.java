package com.test.voucher.controller;

import com.test.voucher.dto.VoucherRequest;
import com.test.voucher.dto.VoucherResponse;
import com.test.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
public class VoucerController {


    @Autowired
    private VoucherService voucherService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateVoucher(@RequestBody VoucherRequest request) {
        VoucherResponse voucherResponser= voucherService.generateVoucher(request);
        String voucherCode = null;
        return ResponseEntity.ok(voucherCode);
    }

    @PostMapping("/validate")
    public ResponseEntity<VoucherResponse> validateVoucher(@RequestBody VoucherRequest request) {
        VoucherResponse response = voucherService.validateVoucher(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/valid")
    public ResponseEntity<List<VoucherResponse>> getValidVouchers(@RequestParam String email) {
        List<VoucherResponse> vouchers = voucherService.getValidVouchersByEmail(email);
        return ResponseEntity.ok(vouchers);
    }
}
