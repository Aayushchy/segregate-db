package com.esewa.highendrepository;

import com.esewa.entity.KYCFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HighEndFileStorageRepository extends JpaRepository<KYCFile, Long> {
    Optional<KYCFile> findByName(String fileName);

    List<KYCFile> findAllByUserId(Long userId);
}
