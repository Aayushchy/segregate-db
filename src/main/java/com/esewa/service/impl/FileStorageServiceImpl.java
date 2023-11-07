package com.esewa.service.impl;

import com.esewa.entity.KYCFile;
import com.esewa.highendrepository.HighEndFileStorageRepository;
import com.esewa.lowendrepository.LowEndFileStorageRepository;
import com.esewa.service.FileStorageService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${storage.file.path}")
    private String storagePath;
//    private final EntityManagerFactory mysqlEntityManagerFactory;
//    private final EntityManagerFactory postgresqlEntityManagerFactory;
    private final HighEndFileStorageRepository highEndFileStorageRepository;
    private final LowEndFileStorageRepository lowEndFileStorageRepository;


  /*  @Autowired
    public FileStorageServiceImpl(@Qualifier("highEndServerEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory,
//                                  @Qualifier("lowEndServerEntityManagerFactory") EntityManagerFactory postgresqlEntityManagerFactory,
                                  HighEndFileStorageRepository highEndFileStorageRepository) {
        this.mysqlEntityManagerFactory = mysqlEntityManagerFactory;
//        this.postgresqlEntityManagerFactory = postgresqlEntityManagerFactory;
        this.highEndFileStorageRepository = highEndFileStorageRepository;
    }*/
//    @Autowired
//    private MyEntityRepository myEntityRepository;

   /* @Autowired
    @Qualifier("highEndServerJdbcTemplate")
    private JdbcTemplate jdbcTemplate;*/
    @Override
    public void saveFiles(List<MultipartFile> files) {

    }

    @Override
    public void saveFile(MultipartFile file, Long userId) throws IOException {

//        HighEndFileStorageRepository highEndFileStorageRepository = new JpaRepositoryFactory(mysqlEntityManagerFactory.createEntityManager()).getRepository(HighEndFileStorageRepository.class);


        highEndFileStorageRepository.save(KYCFile.builder()
                .name(file.getOriginalFilename())
                .path(StringUtils.join(storagePath, file.getOriginalFilename()))
                .type(file.getContentType())
                .userId(userId)
                .build());

//        FileStorageRepository fileStorageRepositoryPost = new JpaRepositoryFactory(postgresqlEntityManagerFactory.createEntityManager()).getRepository(FileStorageRepository.class);
        lowEndFileStorageRepository.save(KYCFile.builder()
                .name(file.getOriginalFilename())
                .path(StringUtils.join(storagePath, file.getOriginalFilename()))
                .type(file.getContentType())
                .userId(userId)
                .build());

        file.transferTo(new File(storagePath + file.getOriginalFilename()));
    }

    @Override
    public byte[] readFile(String fileName) throws IOException {
            Optional<KYCFile> fileData = highEndFileStorageRepository.findByName(fileName);
            String filePath = fileData.get().getPath();
            byte[] file = Files.readAllBytes(new File(filePath).toPath());
            return file;
    }
}
