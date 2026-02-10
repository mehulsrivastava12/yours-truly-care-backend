package com.example.skinsaathi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.skinsaathi.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Modifying
    @Query("UPDATE Address a SET a.defaultAddress = false WHERE a.user.id = :userId")
    void clearDefaultForUser(@Param("userId") Long userId);

    Optional<Address> findByIdAndUserId(Long id, Long userId);
    boolean existsByUserIdAndDefaultAddressTrue(Long userId);
    List<Address> findAllByUserId(Long userId);
}
