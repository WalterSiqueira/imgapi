package com.walter.projects.imgapi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image salvarImagem(MultipartFile file) throws IOException {
        Path directory = Paths.get(uploadDir);

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        Path filePath = directory.resolve(file.getOriginalFilename());

        file.transferTo(filePath.toFile());

        Image image = new Image(file.getOriginalFilename(), filePath.toString());
        return imageRepository.save(image);
    }
}
