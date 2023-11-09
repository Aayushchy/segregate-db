package com.esewa.controller;

import com.esewa.dto.FileDto;
import com.esewa.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FileStorageService fileStorageService;

    @GetMapping("/read/{filename}")
    public ResponseEntity<byte[]> readFile(@PathVariable("filename") String filename) throws IOException {

        FileDto file = fileStorageService.displayFile(filename);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(file.getContentType()))
                .body(file.getData());
    }

/*    @PostMapping("/verify/kyc")
    public ResponseEntity<String> verifyKYC() {

    }*/

}
