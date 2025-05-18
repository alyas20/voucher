package com.test.voucher.service;

import com.test.voucher.dto.VoucherRequest;
import com.test.voucher.dto.VoucherResponse;
import com.test.voucher.entity.Recipient;
import com.test.voucher.entity.SpecialOffer;
import com.test.voucher.entity.Voucher;
import com.test.voucher.repository.RecipientRepo;
import com.test.voucher.repository.SpecialOfferRepo;
import com.test.voucher.repository.VoucherRepo;
import com.test.voucher.util.CommonUtil;
import com.test.voucher.util.VoucherCodeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VoucherServiceImpl implements  VoucherService{


    private final SpecialOfferRepo specialOfferRepo;
    private final RecipientRepo recipientRepo;
    private final VoucherRepo voucherRepo;

    /**
     * Constructor-based dependency injection for better testability and immutability.
     *
     * @param specialOfferRepo The repository for special offers.
     * @param recipientRepo The repository for recipients.
     * @param voucherRepo The repository for vouchers.
     */
    public VoucherServiceImpl(SpecialOfferRepo specialOfferRepo, RecipientRepo recipientRepo, VoucherRepo voucherRepo) {
        this.specialOfferRepo = specialOfferRepo;
        this.recipientRepo = recipientRepo;
        this.voucherRepo = voucherRepo;
    }

    /**
     * This method generates voucher codes based on a special offer and recipient emails.
     * Each voucher is associated with a recipient and a special offer and saved to the database.
     *
     * @param voucherRequest The request object containing special offer name, expiry date, and recipient emails.
     */
    @Override
    public void generateVoucher(VoucherRequest voucherRequest) {
        String specialOfferName = voucherRequest.getSpecialOffer();
        Date expDate = CommonUtil.convertStringToDate(voucherRequest.getExpDate());
        List<String> emails = voucherRequest.getEmails();

        // Retrieve the special offer by special offer name :: will throw runtime exception if not found
        SpecialOffer specialOffer = specialOfferRepo.findByName(specialOfferName)
                .orElseThrow(() -> new RuntimeException("Special Offer not found"));

        // Looping Emails
        for(String email: emails){

            // Retrieve the recipient by email :: will throw runtime exception if not found
            Recipient  recipient = recipientRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Recipient not found"));

            //Generate code by using util VoucherCodeUtil
            String code = VoucherCodeUtil.generateCode();

            // Create a new voucher
            Voucher voucherCode = Voucher.builder()
                    .generatedCode(code)
                    .expDate(expDate)
                    .isUsed(false)
                    .recipient(recipient)
                    .specialOffer(specialOffer)
                    .build();

            // Save it to the database
            voucherRepo.save(voucherCode);
        }
    }

    /**
     * This method validates a voucher code against an email address.
     * It checks if the voucher exists, belongs to the specified email, and is not expired or used.
     * If valid, it marks the voucher as used and sets the usage date.
     *
     * @param voucherCode The voucher code to validate.
     * @param email The email address associated with the voucher.
     * @return VoucherResponse containing validation result and details.
     */
    @Override
    public VoucherResponse validateVoucher(String voucherCode,String email) {
        Date now = new Date();

        // Retrieve the voucher by generated code
        Voucher voucher = voucherRepo.findByGeneratedCode(voucherCode).orElse(null);

        // Return 'Voucher code not found' if voucher is null
        if (voucher == null) {
            return new VoucherResponse(false, null, "Voucher code not found", null, null);
        }

        // Verify that the voucher belongs to the specified email
        Recipient recipient = voucher.getRecipient();
        if(recipient == null || !email.equalsIgnoreCase(voucher.getRecipient().getEmail())){
            return new VoucherResponse(false, null, "Voucher code not belong to this email", null, null);
         }

        // Check if the voucher is expired
        if(voucher.getExpDate() != null && voucher.getExpDate().before(now)){
            return new VoucherResponse(false, null, "Voucher is expired", null, null);
        }

        // Check if the voucher has already been used
        if(Boolean.TRUE.equals(voucher.getIsUsed())){
            return new VoucherResponse(false, null, "Voucher has been used", null, null);
        }

        // Flagging the voucher as used and set the usage date
        voucher.setIsUsed(true);
        voucher.setUsageDate(now);
        voucherRepo.save(voucher);

        // Retrieve the fixed discount percentage from the special offer
        Integer fixedPercentDiscount = voucher.getSpecialOffer().getFixedPercentDiscount();

        return new VoucherResponse(true, fixedPercentDiscount, "Voucher is valid", null, null);
    }

    /**
     * This method retrieves all valid (unused and not expired) vouchers associated with a given email.
     *
     * @param email The email address to search for valid vouchers.
     * @return List of VoucherResponse containing voucher details.
     */
    @Override
    public List<VoucherResponse> getValidVouchersByEmail(String email) {
        List<VoucherResponse> voucherResponses = new ArrayList<>();

        // Fetch all valid vouchers associated with the email
        List<Voucher> validVouchers = voucherRepo.findValidVouchersByEmail(email);

        for (Voucher voucher : validVouchers) {
            // Build a response object for each valid voucher
            VoucherResponse response = VoucherResponse.builder()
                    .voucherCode(voucher.getGeneratedCode())
                    .specialOffer(voucher.getSpecialOffer().getName())
                    .build();

            voucherResponses.add(response);
        }

        return voucherResponses;
    }
}
