package com.yelload.agro_news.service;

import com.yelload.agro_news.entity.AboutUs;
import com.yelload.agro_news.entity.Journalist;
import com.yelload.agro_news.repository.JournalistRepository;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class JournalistService {
    @Autowired
    private JournalistRepository journalistRepository;
    public List<Journalist> getAllJournalists() {
        return journalistRepository.findAll();
    }

    public Journalist getJournalistById(Long id) throws NotFoundException {
        return journalistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Journalist not found"));
    }

    public Journalist createJournalist(MultipartFile image, String title, String fullname) throws IOException {

        String fileName = UUID.randomUUID().toString();
        String fileExtension = "." + FilenameUtils.getExtension(image.getOriginalFilename());
        String filePath = "//var//www//html//agronews//" + fileName + fileExtension;
        Journalist journalist = new Journalist();
        journalist.setTitle(title);
        journalist.setFullname(fullname);
        journalist.setImageData(image.getBytes());
        journalist.setName(fileName);
        journalist.setFileType(image.getContentType());
        try {
            Files.write(Paths.get(filePath), image.getBytes());
            return journalistRepository.save(journalist);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Journalist updateJournalist(Long id, Journalist journalist, MultipartFile image) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString();
            String fileExtension = "." + FilenameUtils.getExtension(image.getOriginalFilename());
            String filePath = "//var//www//html//agronews//" + fileName + fileExtension;
            Journalist existingJournalist =journalistRepository.findById(id).get();
            existingJournalist.setTitle(journalist.getTitle());
            existingJournalist.setFullname(journalist.getFullname());
            existingJournalist.setImageData(image.getBytes());
            existingJournalist.setName(fileName);
            existingJournalist.setFileType(image.getContentType());
            Files.write(Paths.get(filePath), image.getBytes());
            return journalistRepository.save(existingJournalist);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String deleteJournalist( Long id) {
        try {
            journalistRepository.findById(id);
            journalistRepository.deleteById(id);
            return "Journalist deleted successfully";
        } catch (Exception e) {
            return "Journalist not found";
        }
    }
}
