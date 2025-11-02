package com.data.analyse.repository;

import com.data.analyse.entity.CsvData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsvDataRepository extends JpaRepository<CsvData, Long> {
    List<CsvData> findByFileName(String fileName);
    List<CsvData> findByColumn1(String column1);
    List<CsvData> findByColumn2(String column2);
    List<CsvData> findByColumn3(String column3);
    long countByFileName(String fileName);
    void deleteByFileName(String fileName);
}