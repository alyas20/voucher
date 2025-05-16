package com.test.voucher.repository;


import com.test.voucher.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipientRepo extends JpaRepository<Recipient, String> {
    Optional<Recipient> findByEmail(String email);
}
