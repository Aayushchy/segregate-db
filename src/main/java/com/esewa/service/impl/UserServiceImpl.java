package com.esewa.service.impl;

import com.esewa.entity.Users;
import com.esewa.enums.Status;
import com.esewa.highendrepository.UserRepository;
import com.esewa.service.FileStorageService;
import com.esewa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<String> verifyKYC(MultipartFile file, Long userId) {
        try {
            fileStorageService.saveFile(file,userId);
            return ResponseEntity.status(HttpStatus.OK).body("KYC submitted. PLZ check after 24 hrs");
        } catch (Exception e) {
            log.error("Could not upload the file: {}", e.getMessage() );
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Request Failed!");
        }
    }

    @Override
    public void save(Users users) {
        users.setVerificationStatus(Status.UNVERIFIED);
        userRepository.save(users);
    }
}
