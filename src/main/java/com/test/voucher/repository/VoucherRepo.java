package com.test.voucher.repository;

import com.test.voucher.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoucherRepo extends JpaRepository <Voucher, String>{
    Optional<Voucher> findByGeneratedCode(String generatedCode);


    @Query("""
        SELECT v
        FROM Voucher v
        WHERE v.recipient.email = :email
        AND v.isUsed = false
        AND v.expDate >= CURRENT_DATE
    """)
    List<Voucher> findValidVouchersByEmail(String email);
}
