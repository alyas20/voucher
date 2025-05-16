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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements  VoucherService{

    @Autowired
    SpecialOfferRepo specialOfferRepo;

    @Autowired
    RecipientRepo recipientRepo;

    @Autowired
    VoucherRepo voucherRepo;

    @Override
    public void generateVoucher(VoucherRequest voucherRequest) {
        String specialOfferName = voucherRequest.getSpecialOffer();
        Date expDate = CommonUtil.convertStringToDate(voucherRequest.getExpDate());
        List<String> emails = voucherRequest.getEmails();

        SpecialOffer specialOffer = specialOfferRepo.findByName(specialOfferName)
                .orElseThrow(() -> new RuntimeException("Special Offer not found"));

        for(String email: emails){

            Recipient  recipient = recipientRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Recipient not found"));

            String code = VoucherCodeUtil.generateCode();

            Voucher voucherCode = Voucher.builder()
                    .generatedCode(code)
                    .expDate(expDate)
                    .isUsed(false)
                    .recipient(recipient)
                    .specialOffer(specialOffer)
                    .build();

            voucherRepo.save(voucherCode);
        }
    }

    @Override
    public VoucherResponse validateVoucher(String voucherCode,String email) {
        Date now = new Date();

        Voucher voucher = voucherRepo.findByGeneratedCode(voucherCode).orElse(null);

        if (voucher == null) {
            return new VoucherResponse(false, null, "Voucher code not found", null, null);
        }

        Recipient recipient = voucher.getRecipient();
        if(recipient == null || !email.equalsIgnoreCase(voucher.getRecipient().getEmail())){
            return new VoucherResponse(false, null, "Voucher code not belong to this email", null, null);
         }

        if(voucher.getExpDate() != null && voucher.getExpDate().after(now)){
            return new VoucherResponse(false, null, "Voucher is expired", null, null);
        }

        if(voucher.getIsUsed()){
            return new VoucherResponse(false, null, "Voucher has been used", null, null);
        }

        voucher.setIsUsed(true);
        voucher.setUsageDate(now);
        voucherRepo.save(voucher);

        Integer fixedPercentDiscount = voucher.getSpecialOffer().getFixedPercentDiscount();

        return new VoucherResponse(true, fixedPercentDiscount, "Voucher is valid", null, null);
    }

    @Override
    public List<VoucherResponse> getValidVouchersByEmail(String email) {
        List<VoucherResponse> voucherResponses = new ArrayList<>();

        List<Voucher> validVouchers = voucherRepo.findValidVouchersByEmail(email);

        for (Voucher voucher : validVouchers) {
            VoucherResponse response = VoucherResponse.builder()
                    .voucherCode(voucher.getGeneratedCode())
                    .specialOffer(voucher.getSpecialOffer().getName())
                    .build();

            voucherResponses.add(response);
        }

        return voucherResponses;
    }
}
