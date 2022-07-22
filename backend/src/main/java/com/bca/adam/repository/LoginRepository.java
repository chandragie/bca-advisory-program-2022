package com.bca.adam.repository;

import java.util.Optional;

import com.bca.adam.model.Login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LoginRepository extends JpaRepository<Login, String> {
    Optional<Login> findBySessionidAndIsValidTrue(String sessionid);

    @Modifying
    @Query("UPDATE Login set updatedDate = CURRENT_TIMESTAMP, isValid = FALSE WHERE userId = ?1")
    int logout(String userId);
}
