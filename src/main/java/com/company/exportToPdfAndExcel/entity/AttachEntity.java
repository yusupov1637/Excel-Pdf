package com.company.exportToPdfAndExcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "attach")
@Getter
@Setter
public class AttachEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String path;
    @Column
    private String extension;
    @Column
    private String origenName;
    @Column
    private Long size;

    @Column
    private LocalDateTime createdDate;
}
