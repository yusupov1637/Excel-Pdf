package com.company.exportToPdfAndExcel.repository;

import com.company.exportToPdfAndExcel.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity,Integer> {
}
