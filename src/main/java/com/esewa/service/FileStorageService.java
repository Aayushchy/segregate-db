package com.esewa.service;

import com.esewa.dto.FileDto;
import com.esewa.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    void saveFile(MultipartFile file, Long userId);

    FileResponse readFile(Long userId);

    void saveFiles(List<MultipartFile> files);

    FileDto displayFile(String fileName);

}
