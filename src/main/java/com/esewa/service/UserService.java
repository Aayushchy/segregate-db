package com.esewa.service;

import com.esewa.entity.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseEntity<String> verifyKYC(MultipartFile file, Long userId);

    void save(Users users);

}
