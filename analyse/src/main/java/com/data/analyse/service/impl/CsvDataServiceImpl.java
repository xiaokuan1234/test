package com.data.analyse.service.impl;

import com.data.analyse.entity.CsvData;
import com.data.analyse.repository.CsvDataRepository;
import com.data.analyse.service.CsvDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CsvDataServiceImpl implements CsvDataService {
    @Autowired
    private CsvDataRepository csvDataRepository;
    
    @Override
    public CsvData save(CsvData csvData) {
        return csvDataRepository.save(csvData);
    }
    
    @Override
    public List<CsvData> saveAll(List<CsvData> csvDataList) {
        return csvDataRepository.saveAll(csvDataList);
    }
    
    @Override
    public Optional<CsvData> findById(Long id) {
        return csvDataRepository.findById(id);
    }
    
    @Override
    public List<CsvData> findAll() {
        return csvDataRepository.findAll();
    }
    
    @Override
    public Page<CsvData> findAll(Pageable pageable) {
        return csvDataRepository.findAll(pageable);
    }
    
    @Override
    public List<CsvData> findByFileName(String fileName) {
        return csvDataRepository.findByFileName(fileName);
    }
    
    @Override
    public List<CsvData> findByColumn1(String column1) {
        return csvDataRepository.findByColumn1(column1);
    }
    
    @Override
    public List<CsvData> findByColumn2(String column2) {
        return csvDataRepository.findByColumn2(column2);
    }
    
    @Override
    public List<CsvData> findByColumn3(String column3) {
        return csvDataRepository.findByColumn3(column3);
    }
    
    @Override
    public long count() {
        return csvDataRepository.count();
    }
    
    @Override
    public long countByFileName(String fileName) {
        return csvDataRepository.countByFileName(fileName);
    }
    
    @Override
    public void deleteById(Long id) {
        csvDataRepository.deleteById(id);
    }
    
    @Override
    public void deleteByFileName(String fileName) {
        csvDataRepository.deleteByFileName(fileName);
    }
    
    @Override
    public void deleteAll() {
        csvDataRepository.deleteAll();
    }
}