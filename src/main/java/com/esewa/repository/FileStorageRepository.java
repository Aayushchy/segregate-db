package com.esewa.repository;

import com.esewa.entity.KYCFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<KYCFile, Long> {
    Optional<KYCFile> findByName(String fileName);
}
