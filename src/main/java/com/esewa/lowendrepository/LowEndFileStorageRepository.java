package com.esewa.lowendrepository;

import com.esewa.entity.KYCFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LowEndFileStorageRepository extends JpaRepository<KYCFile, Long> {
    Optional<KYCFile> findByName(String fileName);
}
