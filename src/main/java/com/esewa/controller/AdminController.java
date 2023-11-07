package com.esewa.controller;

import com.esewa.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FileStorageService fileStorageService;

    //Read from high end server
    //verify and change status to verified/Unverified
    //Unverified: Reupload
    //Verified then write to low end server
    //write to high end server
    @GetMapping("/read/{filename}")
    public ResponseEntity<byte[]> readFile(@PathVariable("filename") String filename) throws IOException {

        byte[] file = fileStorageService.readFile(filename);
        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf(f))
                .body(file);
    }

/*    @PostMapping("/verify/kyc")
    public ResponseEntity<String> verifyKYC() {

    }*/

}
