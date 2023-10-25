package com.yelload.agro_news.service;

import com.yelload.agro_news.entity.AboutUs;
import com.yelload.agro_news.repository.AboutUsRepository;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AboutUsService {
    @Autowired
    private AboutUsRepository aboutUsRepository;
    public List<AboutUs> getAllAboutUs() {
        return aboutUsRepository.findAll();
    }

    public AboutUs getAboutUsById(Long id) throws NotFoundException {
        return aboutUsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AboutUs item not found"));
    }

    public AboutUs createAboutUs(MultipartFile image, String title, String description,boolean isMain) throws IOException {

        String fileName = UUID.randomUUID().toString();
        String fileExtension = "." + FilenameUtils.getExtension(image.getOriginalFilename());
        String filePath = "C:\\Users\\nigarorucova\\Desktop\\Aqro\\aqroxeber\\src\\main\\java\\com\\yelload\\agro_news\\images\\" + fileName + fileExtension;
        AboutUs aboutUs = new AboutUs();
        aboutUs.setTitle(title);
        aboutUs.setDescription(description);
        aboutUs.setIsMain(isMain);
        aboutUs.setImageData(image.getBytes());
        aboutUs.setName(fileName);
        aboutUs.setFileType(image.getContentType());
        try {
            Files.write(Paths.get(filePath), image.getBytes());
            return aboutUsRepository.save(aboutUs);
        } catch (IOException e) {
            // Hata işleme
            e.printStackTrace();
            return null;
        }
    }
    public AboutUs updateAboutUs(Long id, AboutUs aboutUs,MultipartFile image) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString();
            String fileExtension = "." + FilenameUtils.getExtension(image.getOriginalFilename());
            String filePath = "C:\\Users\\nigarorucova\\Desktop\\Aqro\\aqroxeber\\src\\main\\java\\com\\yelload\\agro_news\\images\\" + fileName + fileExtension;
            AboutUs existingAboutUs =aboutUsRepository.findById(id).get();
            existingAboutUs.setTitle(aboutUs.getTitle());
            existingAboutUs.setDescription(aboutUs.getDescription());
            existingAboutUs.setIsMain(aboutUs.getIsMain());
            existingAboutUs.setImageData(image.getBytes());
            existingAboutUs.setName(fileName);
            existingAboutUs.setFileType(image.getContentType());
            Files.write(Paths.get(filePath), image.getBytes());
            return aboutUsRepository.save(existingAboutUs);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String deleteAboutUs( Long id) {
        try {
            aboutUsRepository.findById(id);
            aboutUsRepository.deleteById(id);
            return "AboutUs item deleted successfully";
        } catch (Exception e) {
            return "AboutUs item not found";
        }
    }

}
