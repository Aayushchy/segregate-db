package com.esewa.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    void saveFile(MultipartFile file, Long userId) throws IOException;

    byte[] readFile(String fileName) throws IOException;

    void saveFiles(List<MultipartFile> files);
}
