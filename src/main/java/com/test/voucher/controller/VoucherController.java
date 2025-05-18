package com.test.voucher.controller;

import com.test.voucher.dto.VoucherRequest;
import com.test.voucher.dto.VoucherResponse;
import com.test.voucher.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@Tag(name = "Voucher", description = "Voucher management APIs")
public class VoucherController {

    private static final Logger logger = LogManager.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * This API is used to generate a voucher code and store it in the database.
     * @param request The request object containing voucher details (e.g., email, amount, expiryDate).
     * @return ResponseEntity<String> with a success or error message.
     */
    @PostMapping("/generateVoucher")
    @Operation(summary = "Generate Voucher Code", description = "Generate Voucher Code")
    public ResponseEntity<String> generateVoucher(@RequestBody VoucherRequest request) {
        try {
            voucherService.generateVoucher(request);
        } catch (Exception e) {
            logger.error("ERROR API /generate :: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request Has Been Failed Error: "+ e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Request Has Been Success");
    }

    /**
     * This API is used to validate a voucher code against the provided email and set the usage date if valid.
     * @param voucherCode The voucher code to be validated.
     * @param email The email associated with the voucher.
     * @return ResponseEntity<VoucherResponse> containing the voucher details if valid.
     */
    @PostMapping("/validateVoucher")
    @Operation(summary = "Validate Voucher Code", description = "Validate Voucher Code And Set Date Of Usage")
    public ResponseEntity<VoucherResponse> validateVoucher(@RequestParam String voucherCode, @RequestParam String email) {
        VoucherResponse response = voucherService.validateVoucher(voucherCode, email);
        return ResponseEntity.ok(response);
    }

    /**
     * This API retrieves all valid voucher codes associated with the provided email.
     * @param email The email address to fetch vouchers for.
     * @return ResponseEntity<List<VoucherResponse>> containing a list of valid vouchers.
     */
    @GetMapping("/getValidVouchers")
    @Operation(summary = "To Get List Voucher Code", description = "To Get All Valid Voucher Codes By Input Email")
    public ResponseEntity<List<VoucherResponse>> getValidVouchers(@RequestParam String email) {
        List<VoucherResponse> vouchers = voucherService.getValidVouchersByEmail(email);
        return ResponseEntity.ok(vouchers);
    }
}
