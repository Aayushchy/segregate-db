package com.esewa.controller;

import com.esewa.dto.FileResponse;
import com.esewa.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("common")
@RequiredArgsConstructor
public class CommonController {

    private final FileStorageService fileStorageService;

    @GetMapping("/read/file/{userId}")
    public FileResponse readFile(@PathVariable("userId") Long userId) {
        return fileStorageService.readFile(userId);
    }
}
