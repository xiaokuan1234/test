package com.data.analyse.service;

import com.data.analyse.entity.CsvData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CsvDataService {
    CsvData save(CsvData csvData);
    List<CsvData> saveAll(List<CsvData> csvDataList);
    Optional<CsvData> findById(Long id);
    List<CsvData> findAll();
    Page<CsvData> findAll(Pageable pageable);
    List<CsvData> findByFileName(String fileName);
    List<CsvData> findByColumn1(String column1);
    List<CsvData> findByColumn2(String column2);
    List<CsvData> findByColumn3(String column3);
    long count();
    long countByFileName(String fileName);
    void deleteById(Long id);
    void deleteByFileName(String fileName);
    void deleteAll();
}