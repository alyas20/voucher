package com.test.voucher.dataloader;

import com.test.voucher.entity.Recipient;
import com.test.voucher.entity.SpecialOffer;
import com.test.voucher.repository.RecipientRepo;
import com.test.voucher.repository.SpecialOfferRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final SpecialOfferRepo specialOfferRepo;
    private final RecipientRepo recipientRepo;

    /**
     * Constructor-based dependency injection for better testability and immutability.
     *
     * @param specialOfferRepo The repository for special offers.
     * @param recipientRepo The repository for recipients.
     */
    public DataLoader(SpecialOfferRepo specialOfferRepo, RecipientRepo recipientRepo) {
        this.specialOfferRepo = specialOfferRepo;
        this.recipientRepo = recipientRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        Recipient recipient1 = new Recipient();
        recipient1.setName("ALYAS 1");
        recipient1.setEmail("alyasamsyar@gmail.com");
        recipientRepo.save(recipient1);

        Recipient recipient2 = new Recipient();
        recipient2.setName("ALYAS 2");
        recipient2.setEmail("alyasamsyar@hotmail.com");
        recipientRepo.save(recipient2);

        SpecialOffer specialOffer1 =  new SpecialOffer();
        specialOffer1.setName("Hari Jadi");
        specialOffer1.setFixedPercentDiscount(30);
        specialOfferRepo.save(specialOffer1);

        SpecialOffer specialOffer2 =  new SpecialOffer();
        specialOffer2.setName("Hari Pekerja");
        specialOffer2.setFixedPercentDiscount(50);
        specialOfferRepo.save(specialOffer2);

    }
}