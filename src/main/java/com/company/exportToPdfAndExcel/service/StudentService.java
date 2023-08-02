package com.company.exportToPdfAndExcel.service;

import com.company.exportToPdfAndExcel.dto.AttachDTO;
import com.company.exportToPdfAndExcel.dto.StudentDTO;
import com.company.exportToPdfAndExcel.entity.AttachEntity;
import com.company.exportToPdfAndExcel.entity.StudentEntity;
import com.company.exportToPdfAndExcel.repository.AttachRepository;
import com.company.exportToPdfAndExcel.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttachService attachService;
    @Autowired
    private AttachRepository attachRepository;

    public Page<StudentEntity> findAll(int currentPage, int size) {
        Pageable p = PageRequest.of(currentPage, size);
        return studentRepository.findAll(p);
    }

    public List<StudentEntity> listAll() {
        return studentRepository.findAll();
    }

    public StudentDTO toDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setName(studentEntity.getName());
        studentDTO.setSurname(studentEntity.getSurname());
        studentDTO.setFamilyName(studentEntity.getFamilyName());
        studentDTO.setBirthPlace(studentEntity.getBirthPlace());
        studentDTO.setStudentPhotoID(studentEntity.getAttachEntity().getId());
        studentDTO.setPhone(studentEntity.getPhone());
        studentDTO.setBirthday(studentEntity.getBirthday());
        studentDTO.setGender(studentEntity.getGender());
        studentDTO.setCitizenship(studentEntity.getCitizenship());
        studentDTO.setPassportSerial(studentEntity.getPassportSerial());
        studentDTO.setPassportNumber(studentEntity.getPassportNumber());
        studentDTO.setPinfl(studentEntity.getPinfl());
        studentDTO.setIssueDate(studentEntity.getIssueDate());
        studentDTO.setExpiryDate(studentEntity.getExpiryDate());
        studentDTO.setWorkplace(studentEntity.getWorkplace());
        studentDTO.setMarriageStatus(studentEntity.getMarriageStatus());
        return studentDTO;
    }

    public StudentEntity toEntity(StudentDTO studentDTO, MultipartFile studentPhoto) {
        StudentEntity studentEntity = new StudentEntity();
        if (studentDTO.getId() != null) {
            studentEntity.setId(studentDTO.getId());
        }
        studentEntity.setName(studentDTO.getName());
        studentEntity.setSurname(studentDTO.getSurname());
        studentEntity.setFamilyName(studentDTO.getFamilyName());
        studentEntity.setBirthPlace(studentDTO.getBirthPlace());

        AttachDTO upload = attachService.upload(studentPhoto);
        String id = upload.getId();
        Optional<AttachEntity> byId = attachRepository.findById(id);
        if (byId.isEmpty()) {
            throw new NoSuchElementException("Image not found");
        }

        studentEntity.setAttachEntity(byId.get());
        studentEntity.setPhone(studentDTO.getPhone());
//        if (LocalDate.parse(studentDTO.getBirthday()).isAfter(LocalDate.now())){
//            throw new DateTimeException("after from now");
//        }
        studentEntity.setBirthday(studentDTO.getBirthday());
        studentEntity.setGender(studentDTO.getGender());
        studentEntity.setCitizenship(studentDTO.getCitizenship());
        studentEntity.setPassportSerial(studentDTO.getPassportSerial());
        studentEntity.setPassportNumber(studentDTO.getPassportNumber());
        studentEntity.setPinfl(studentDTO.getPinfl());
        studentEntity.setIssueDate(studentDTO.getIssueDate());
        studentEntity.setExpiryDate(studentDTO.getExpiryDate());
        studentEntity.setWorkplace(studentDTO.getWorkplace());
        studentEntity.setMarriageStatus(studentDTO.getMarriageStatus());
        return studentEntity;
    }

    public void saveStudent(StudentDTO dto, MultipartFile studentPhoto) {
        StudentEntity studentEntity = toEntity(dto, studentPhoto);
        studentRepository.save(studentEntity);
    }

    public void update(StudentDTO dto, MultipartFile studentPhoto) {
        StudentEntity studentEntity = toEntity(dto, studentPhoto);
        studentRepository.save(studentEntity);
    }

    @Transactional
    public void deleteById(Integer id) {
        studentRepository.deleteById(id);
    }

    public StudentDTO findById(Integer id) {
        Optional<StudentEntity> byId = studentRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        return toDTO(byId.get());
    }
}
