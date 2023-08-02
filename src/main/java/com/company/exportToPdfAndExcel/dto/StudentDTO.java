package com.company.exportToPdfAndExcel.dto;

import com.company.exportToPdfAndExcel.entity.AttachEntity;
import com.company.exportToPdfAndExcel.enums.Gender;
import com.company.exportToPdfAndExcel.enums.MarriageStatus;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;


@Getter
@Setter
public class StudentDTO {
    private Integer id;

    @Pattern(regexp = "(^[a-zA-Z]{3,40}$)",message = "name wrong")
    private String name;
    @Pattern(regexp = "(^[a-zA-Z]*$)",message = "surname wrong")
    @Size(min = 3, max = 50,message = "surname")
    private String surname;
    @Pattern(regexp = "(^[a-zA-Z]*$)",message = "family name wrong")
    @Size(min = 3, max = 50)
    private String familyName;

    @NotBlank(message = "Please enter your family name")
    @Size(min = 3, max = 50)
    private String birthPlace;

    private String studentPhotoID;
    @Pattern(regexp = "(^[0-9]{12}$)",message = "wrong phone number")
    private String phone = "998";
    @Past(message = "past birth")
    private LocalDate birthday;
    private Gender gender;
    private String citizenship;
    @Pattern(regexp = "(^[A-Z]*$)")
    @Size(min =2,max = 2)
    private String passportSerial;
    @Pattern(regexp = "(^[0-9]{7}$)",message = "wrong passport number")
    private String passportNumber;
    @Pattern(regexp = "(^[0-9]{14}$)",message = "wrong pinfl")
    private String pinfl;
    @PastOrPresent(message = "past issue date")
    private LocalDate issueDate;
    @FutureOrPresent(message = "expiry date")
    private LocalDate expiryDate;

    @Size(min = 3, max = 50)
    private String workplace;
    private MarriageStatus marriageStatus;
}
