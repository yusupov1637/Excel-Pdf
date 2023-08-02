package com.company.exportToPdfAndExcel.controller;


import com.company.exportToPdfAndExcel.dto.AttachDTO;
import com.company.exportToPdfAndExcel.service.AttachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/attach")
@RequiredArgsConstructor
@Slf4j
public class AttachController {

    private final AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        log.info("upload attach  ={}", file.getOriginalFilename());
        return ResponseEntity.ok(attachService.upload(file));
    }


    @GetMapping(value = "/open/{fileName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        log.info("open attach  ={}", fileName);
        return attachService.open_general(fileName);
    }

    @GetMapping(value = "/open/pdf/{fileName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] open_pdf(@PathVariable("fileName") String fileName) {
        log.info("open attach  ={}", fileName);
        return attachService.open_general(fileName);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        log.info("download attach  ={}", fileName);
        return attachService.download(fileName);
    }


}
