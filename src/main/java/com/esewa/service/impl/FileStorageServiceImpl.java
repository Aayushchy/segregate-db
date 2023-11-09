package com.esewa.service.impl;

import com.esewa.constants.ResponseMessage;
import com.esewa.constants.ResponseStatusCode;
import com.esewa.dto.FileDto;
import com.esewa.dto.FileResponse;
import com.esewa.entity.KYCFile;
import com.esewa.exception.FileException;
import com.esewa.highendrepository.HighEndFileStorageRepository;
import com.esewa.lowendrepository.LowEndFileStorageRepository;
import com.esewa.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${storage.file.path}")
    private String storagePath;
    private final HighEndFileStorageRepository highEndFileStorageRepository;
    private final LowEndFileStorageRepository lowEndFileStorageRepository;

    @Override
    public void saveFiles(List<MultipartFile> files) {

    }

    @Override
    public void saveFile(MultipartFile file, Long userId) {

        highEndFileStorageRepository.save(KYCFile.builder()
                .name(file.getOriginalFilename())
                .path(StringUtils.join(storagePath, file.getOriginalFilename()))
                .type(file.getContentType())
                .userId(userId)
                .build());

        lowEndFileStorageRepository.save(KYCFile.builder()
                .name(file.getOriginalFilename())
                .path(StringUtils.join(storagePath, file.getOriginalFilename()))
                .type(file.getContentType())
                .userId(userId)
                .build());
        try {
            file.transferTo(new File(storagePath + file.getOriginalFilename()));
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    @Override
    public FileResponse readFile(Long userId) {
        List<KYCFile> filesData = highEndFileStorageRepository.findAllByUserId(userId);
        if(CollectionUtils.isEmpty(filesData)) {
            log.error("File for userId: {} is not present", userId);
            throw new FileException(ResponseMessage.FILE_NOT_FOUND);
        }
        try {
            List<FileDto> files = filesData.stream()
                    .map(this::prepareFileDto)
                    .toList();
            return prepareFileResponse(files);
        }
        catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
            throw new FileException(ResponseMessage.ERROR_READ_FILE);
        }
    }

    private FileDto prepareFileDto(KYCFile file)  {
        try {
            byte[] fileByte = Files.readAllBytes(new File(file.getPath()).toPath());
            return FileDto.builder()
                    .contentType(file.getType())
                    .name(file.getName())
                    .data(fileByte)
                    .build();
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    private FileResponse prepareFileResponse(List<FileDto> files) {
        return FileResponse.builder()
                .status(HttpStatus.OK.value())
                .files(files)
                .build();
    }

    @Override
    public FileDto displayFile(String fileName) {
        Optional<KYCFile> fileData = highEndFileStorageRepository.findByName(fileName);
        if (fileData.isEmpty())
            throw new FileException(ResponseMessage.FILE_NOT_FOUND);
        String filePath = fileData.get().getPath();
        try {
            byte[] file= Files.readAllBytes(new File(filePath).toPath());
            return FileDto.builder()
                    .contentType(fileData.get().getType())
                    .data(file)
                    .build();
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }
}
