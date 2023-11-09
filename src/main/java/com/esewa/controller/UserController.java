package com.esewa.controller;

import com.esewa.entity.Users;
import com.esewa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users usersDetail) {
        userService.save(usersDetail);
        return ResponseEntity.ok("Saved Successfully");
    }

    //submit files for kyc verification
    //After verification: Some time they may read the file uploaded from low end server
    @PostMapping("/submit/kyc/files/{userId}")
    public ResponseEntity<String> requestForKYCVerification(@RequestPart("files") MultipartFile file, @PathVariable("userId") Long userId) {
       return userService.verifyKYC(file, userId);
       //KYC submitted. PLZ check after 24 hrs
    }



}
