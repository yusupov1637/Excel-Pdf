package com.company.exportToPdfAndExcel.controller;

import com.company.exportToPdfAndExcel.util.PdfGenerator;
import com.company.exportToPdfAndExcel.util.StudentExcelExporter;
import com.company.exportToPdfAndExcel.dto.StudentDTO;
import com.company.exportToPdfAndExcel.entity.StudentEntity;
import com.company.exportToPdfAndExcel.service.StudentService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private PdfGenerator pdfGenerator;

    public StudentController(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping({"/", ""})
    public String studentsPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "100") int size, Model model) {
        Page<StudentEntity> studentEntityPage = studentService.findAll(page - 1, size);
        List<StudentEntity> studentEntities = studentEntityPage.getContent();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        studentEntities.forEach(studentEntity -> studentDTOS.add(studentService.toDTO(studentEntity)));
        model.addAttribute("dtoList", studentDTOS);
        model.addAttribute("students", studentEntities);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentEntityPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("totalItem", studentEntityPage.getTotalElements());
        return "student_list";
    }

    @GetMapping("/go/add")
    public String goToAdd(Model model) {
        model.addAttribute("studentObj", new StudentDTO());
        model.addAttribute("isUpdate", false);
        return "student_add";
    }

    @GetMapping("/go/update/{id}")
    public String goToUpdate(Model model, @PathVariable("id") Integer id) {
        StudentDTO byId = studentService.findById(id);
        LocalDate birthdayStr = byId.getBirthday();
        model.addAttribute("studentObj", byId);
        model.addAttribute("birthdayStr", birthdayStr);
        model.addAttribute("isUpdate", true);
        return "student_add";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("student") StudentDTO studentDTO,
                                @RequestParam("studentPhoto") MultipartFile studentPhoto) {
        studentService.saveStudent(studentDTO, studentPhoto);
        return "redirect:/student";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@ModelAttribute StudentDTO dto, @PathVariable Integer id) {
        studentService.deleteById(id);
        return "redirect:/student";
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=students_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<StudentEntity> studentEntityList = studentService.listAll();
        StudentExcelExporter excelExporter = new StudentExcelExporter(studentEntityList);
        excelExporter.export(response);
    }

    @GetMapping(value = "/export/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> exportToPDF(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        StudentDTO byId = studentService.findById(id);
        ByteArrayInputStream outputStream = pdfGenerator.createPdf2(byId);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=student.pdf");

        return ResponseEntity.ok().headers(headers).contentType
                        (MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(outputStream));
    }

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "student.pdf");
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//
//        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);


//    @GetMapping("/export/pdf/{id}")
//    public void exportToPDF(@PathVariable Integer id, HttpServletResponse response) throws DocumentException, IOException {
//        response.setContentType("application/pdf");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=ABOUT_STUDENT_" + currentDateTime + ".pdf";
//        response.setHeader(headerKey, headerValue);
//        StudentDTO byId = studentService.findById(id);
//
//
//        PdfGenerator exporter = new PdfGenerator(byId);
//        exporter.export(response);
//
//    }

    @PostMapping("/add")
    public String addStudent(@Valid @RequestBody @ModelAttribute("student") StudentDTO dto,
                             @RequestParam("studentPhoto") MultipartFile studentPhoto) {
        studentService.saveStudent(dto, studentPhoto);
        return "redirect:/student";
    }

    @GetMapping("/pdf/{studentId}")
    public String pdf(@PathVariable("studentId") Integer id, Model model) {
        StudentDTO byId = studentService.findById(id);
        model.addAttribute("student", byId);
        return "student_profile";
    }
}
