package com.company.exportToPdfAndExcel.service;

import com.company.exportToPdfAndExcel.dto.AttachDTO;
import com.company.exportToPdfAndExcel.entity.AttachEntity;
import com.company.exportToPdfAndExcel.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachService {


    @Value("${attach.upload.folder}")
    private String attachFolder;

    @Value("${server.domain.name}")
    private String domainName;

    private final AttachRepository attachRepository;


    public AttachDTO upload(MultipartFile file) {

        String pathFolder = getYmDString(); // 2022/04/23

        File folder = new File(attachFolder + pathFolder);

        if (!folder.exists()) {
            boolean create = folder.mkdirs();
        }


        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename())); // dasda.asdas.dasd.jpg

        AttachEntity entity = saveAttach(pathFolder, extension, file);
        AttachDTO dto = toDTO(entity);

        try {
            // uploads/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachFolder + pathFolder + "/" + entity.getId() + "." + extension);
            Files.write(path, bytes);
        } catch (IOException e) {
            log.warn("Upload Attach Exception = {}",e.getMessage());
            e.printStackTrace();
        }
        return dto;
    }

    public ResponseEntity<Resource> download(String key) { // images.png
        try {
            AttachEntity entity = get(key);

            if (entity == null) {
                return null;
            }

            String path = entity.getPath() + "/" + key + "." + entity.getExtension();

            Path file = Paths.get(attachFolder + path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                                "important; filename=\"" + entity.getOrigenName() + "\"")
                        .body(resource);

            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public byte[] open_general(String key) {
        byte[] data;
        try {
            AttachEntity entity = get(key);

            if (entity == null) {
                return null;
            }

            String path = entity.getPath() + "/" + key + "." + entity.getExtension();
            Path file = Paths.get(attachFolder + path);

            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public AttachEntity saveAttach(String pathFolder, String extension, MultipartFile file) {
        AttachEntity entity = new AttachEntity();
        entity.setPath(pathFolder);
        entity.setOrigenName(file.getOriginalFilename());
        entity.setExtension(extension);
        entity.setSize(file.getSize());
        attachRepository.save(entity);
        return entity;
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElse(null);
    }

    public Boolean getAndDelete(String id) {
        AttachEntity entity = get(id);

        if (entity == null) return false;

        String path = attachFolder+  entity.getPath() + "/" + id + "." + entity.getExtension();

        File file = new File(path);

        if(file.exists()){
            return file.delete();
        }

        return false;
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setOrigenName(entity.getOrigenName());
        dto.setPath(entity.getPath());
        dto.setUrl(domainName + "/attach/download/" + entity.getId());
        return dto;
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
    public void deleteAttach(String id){
        attachRepository.deleteCascade(id);
    }
}
