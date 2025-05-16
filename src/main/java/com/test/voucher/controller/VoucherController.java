package com.test.voucher.controller;

import com.test.voucher.dto.VoucherRequest;
import com.test.voucher.dto.VoucherResponse;
import com.test.voucher.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@Tag(name = "Voucher", description = "Voucher management APIs")
public class VoucherController {

    private static final Logger logger = LogManager.getLogger(VoucherController.class);

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/generateVoucher")
    @Operation(summary = "Generate Voucher Code", description = "Generate Voucher Code")
    public ResponseEntity<String> generateVoucher(@RequestBody VoucherRequest request) {
        String voucherCode = null;
        try {
            voucherService.generateVoucher(request);
        } catch (Exception e) {
            logger.error("ERROR API /generate :: {}", e.getMessage());
        }
        return ResponseEntity.ok("Request Has Been Success");
    }

    @PostMapping("/validateVoucher")
    @Operation(summary = "Validate Voucher Code", description = "Validate Voucher Code And Set Date Of Usage")
    public ResponseEntity<VoucherResponse> validateVoucher(@RequestParam String voucherCode, @RequestParam String email) {
        VoucherResponse response = voucherService.validateVoucher(voucherCode, email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getValidVouchers")
    @Operation(summary = "To Get List Voucher Code", description = "To Get All Valid Voucher Codes By Input Email")
    public ResponseEntity<List<VoucherResponse>> getValidVouchers(@RequestParam String email) {
        List<VoucherResponse> vouchers = voucherService.getValidVouchersByEmail(email);
        return ResponseEntity.ok(vouchers);
    }
}
