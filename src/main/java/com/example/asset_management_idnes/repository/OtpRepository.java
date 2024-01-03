package com.example.asset_management_idnes.repository;

import com.example.asset_management_idnes.domain.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtpCode(String otpCode);

    @Modifying
    @Transactional
    @Query("DELETE FROM Otp o WHERE o.user.id = :userId")
    void deleteByUserId(Long userId);
}
