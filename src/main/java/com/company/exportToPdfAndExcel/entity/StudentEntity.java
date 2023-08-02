package com.company.exportToPdfAndExcel.entity;

import com.company.exportToPdfAndExcel.enums.Gender;
import com.company.exportToPdfAndExcel.enums.MarriageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "person_entity")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "family_name")
    private String familyName;
    @Column
    private String birthPlace;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "student_photo_id")
    private AttachEntity attachEntity;
    @Column(name = "phone_number")
    private String phone;
    @Column
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "citizenship")
    private String citizenship;
    @Column(name = "passport_serial")
    private String passportSerial;
    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "pinfl")
    private String pinfl;
    @Column(name = "passport_issue_date")
    private LocalDate issueDate;

    @Column(name = "passport_expiry_date")
    private LocalDate expiryDate;

    @Column(name = "workplace")
    private String workplace;

    @Enumerated(EnumType.STRING)
    @Column(name = "marriage_status")
    private MarriageStatus marriageStatus;
}
