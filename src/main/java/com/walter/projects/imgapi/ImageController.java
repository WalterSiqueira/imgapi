package com.walter.projects.imgapi;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/img")
public class ImageController {
    private  ImageService imageService;
    private  ImageRepository imageRepository;

    @Autowired
    public void ImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @Autowired
    public void ImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/all")
    public List<Image> listarImagens() {
        return imageRepository.findAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Image savedImage = imageService.salvarImagem(file);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
