package com.test.voucher.repository;

import com.test.voucher.entity.Recipient;
import com.test.voucher.entity.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialOfferRepo extends JpaRepository<SpecialOffer, String> {
    Optional<SpecialOffer> findByName (String name);
}