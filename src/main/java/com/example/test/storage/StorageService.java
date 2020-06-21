package com.example.test.storage;

import com.example.test.UsersContainer;
import com.example.test.XmlConverter;
import com.example.test.entity.Users;
import com.example.test.storage.StorageException;
import com.example.test.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class StorageService {

    private final Path rootLocation;
    private final XmlConverter xmlConverter;

    @Autowired
    public StorageService(StorageProperties properties, XmlConverter xmlConverter) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.xmlConverter = xmlConverter;
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UsersContainer storeFile(MultipartFile file, String fileName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
                return parseAndPersist(fileName);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }

    }
    private UsersContainer parseAndPersist(String filename) {
        Users users = xmlConverter.xmlToObject(filename);
        UsersContainer usersContainer = UsersContainer.fromUsers(users);
        return usersContainer;
    }

}
