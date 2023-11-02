package com.yelload.agro_news.service;

import com.yelload.agro_news.entity.Referance;
import com.yelload.agro_news.repository.ReferanceRepository;
import javassist.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ReferanceService {
    @Autowired
    private ReferanceRepository referanceRepository;
    public List<Referance> getAllReferances() {
        return referanceRepository.findAll();
    }

    public Referance getReferanceById(Long id) throws NotFoundException {
        return referanceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Referance not found"));
    }
    public Referance createReferance(MultipartFile image) throws IOException {

        String fileName = UUID.randomUUID().toString();
        String fileExtension = "." + FilenameUtils.getExtension(image.getOriginalFilename());
        String filePath = "//var//www//html//agronews//" + fileName + fileExtension;
        Referance referance = new Referance();
        referance.setImageData(image.getBytes());
        referance.setName(fileName);
        referance.setFileType(image.getContentType());
        try {
            Files.write(Paths.get(filePath), image.getBytes());
            return referanceRepository.save(referance);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Referance updateReferance(Long id, Referance referance, MultipartFile image) throws IOException {
        try {
            String fileName = UUID.randomUUID().toString();
            String fileExtension = "." + FilenameUtils.getExtension(image.getOriginalFilename());
            String filePath = "//var//www//html//agronews//" + fileName + fileExtension;
            Referance existingReferance =referanceRepository.findById(id).get();
            existingReferance.setImageData(image.getBytes());
            existingReferance.setName(fileName);
            existingReferance.setFileType(image.getContentType());
            Files.write(Paths.get(filePath), image.getBytes());
            return referanceRepository.save(existingReferance);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String deleteReferance( Long id) {
        try {
            referanceRepository.findById(id);
            referanceRepository.deleteById(id);
            return "Referance deleted successfully";
        } catch (Exception e) {
            return "Referance not found";
        }
    }
}
