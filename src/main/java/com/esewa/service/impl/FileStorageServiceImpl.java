package com.esewa.service.impl;

import com.esewa.entity.KYCFile;
import com.esewa.repository.FileStorageRepository;
import com.esewa.service.FileStorageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${storage.file.path}")
    private String storagePath;

    private final FileStorageRepository fileStorageRepository;

    private final ApplicationContext applicationContext;

    private final EntityManagerFactory mysqlEntityManagerFactory;

    private final EntityManagerFactory postgresEntityManagerFactory;

    @Autowired
    public FileStorageServiceImpl(@Qualifier("entityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory,
                                  @Qualifier("lowEndServerEntityManagerFactory") EntityManagerFactory postgresEntityManagerFactory,
                                  FileStorageRepository fileStorageRepository,
                                  ApplicationContext applicationContext) {
        this.fileStorageRepository = fileStorageRepository;
        this.applicationContext = applicationContext;
        this.mysqlEntityManagerFactory = mysqlEntityManagerFactory;
        this.postgresEntityManagerFactory = postgresEntityManagerFactory;
    }
    @Override
    public void saveFiles(List<MultipartFile> files) {

    }

    @Override
    public void saveFile(MultipartFile file, Long userId) throws IOException {

        String[] names = applicationContext.getBeanDefinitionNames();
        saveFile(mysqlEntityManagerFactory, file, userId);
        saveFile(postgresEntityManagerFactory, file, userId);
        file.transferTo(new File(storagePath + file.getOriginalFilename()));
    }

    private void saveFile(EntityManagerFactory entityManagerFactory, MultipartFile file, Long userId) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        FileStorageRepository fileStorageRepository = new JpaRepositoryFactory(entityManager).getRepository(FileStorageRepository.class);

        entityManager.getTransaction().begin();
        fileStorageRepository.save(KYCFile.builder()
                .name(file.getOriginalFilename())
                .path(StringUtils.join(storagePath, file.getOriginalFilename()))
                .type(file.getContentType())
                .userId(userId)
                .build());
        entityManager.getTransaction().commit();
    }

    @Override
    public byte[] readFile(String fileName) throws IOException {
            Optional<KYCFile> fileData = fileStorageRepository.findByName(fileName);
            String filePath = fileData.get().getPath();
            byte[] file = Files.readAllBytes(new File(filePath).toPath());
            return file;
    }
}
