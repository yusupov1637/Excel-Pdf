package com.company.exportToPdfAndExcel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private String id;
    private String url;
    private String origenName;
    private LocalDateTime createdDate;
    private String path;
}
