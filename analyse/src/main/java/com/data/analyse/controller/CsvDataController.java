package com.data.analyse.controller;

import com.data.analyse.entity.CsvData;
import com.data.analyse.service.CsvDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/csv-data")
public class CsvDataController {
    @Autowired
    private CsvDataService csvDataService;
    
    @PostMapping
    public ResponseEntity<CsvData> createCsvData(@RequestBody CsvData csvData) {
        try {
            CsvData savedCsvData = csvDataService.save(csvData);
            return new ResponseEntity<>(savedCsvData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/batch")
    public ResponseEntity<List<CsvData>> createCsvDataBatch(@RequestBody List<CsvData> csvDataList) {
        try {
            List<CsvData> savedCsvDataList = csvDataService.saveAll(csvDataList);
            return new ResponseEntity<>(savedCsvDataList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<CsvData>> getAllCsvData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        try {
            List<Sort.Order> orders = List.of(sort[0].split(",")).stream()
                    .map(order -> new Sort.Order(Sort.Direction.fromString(order.split(",")[1]), order.split(",")[0]))
                    .toList();
            
            Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
            Page<CsvData> csvDataPage = csvDataService.findAll(pageable);
            
            return new ResponseEntity<>(csvDataPage.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CsvData> getCsvDataById(@PathVariable("id") long id) {
        Optional<CsvData> csvData = csvDataService.findById(id);
        
        return csvData.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/file-name/{fileName}")
    public ResponseEntity<List<CsvData>> getCsvDataByFileName(@PathVariable("fileName") String fileName) {
        try {
            List<CsvData> csvDataList = csvDataService.findByFileName(fileName);
            
            if (csvDataList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(csvDataList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/column1/{column1}")
    public ResponseEntity<List<CsvData>> getCsvDataByColumn1(@PathVariable("column1") String column1) {
        try {
            List<CsvData> csvDataList = csvDataService.findByColumn1(column1);
            
            if (csvDataList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(csvDataList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/column2/{column2}")
    public ResponseEntity<List<CsvData>> getCsvDataByColumn2(@PathVariable("column2") String column2) {
        try {
            List<CsvData> csvDataList = csvDataService.findByColumn2(column2);
            
            if (csvDataList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(csvDataList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/column3/{column3}")
    public ResponseEntity<List<CsvData>> getCsvDataByColumn3(@PathVariable("column3") String column3) {
        try {
            List<CsvData> csvDataList = csvDataService.findByColumn3(column3);
            
            if (csvDataList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(csvDataList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getCsvDataCount() {
        try {
            long count = csvDataService.count();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/count/{fileName}")
    public ResponseEntity<Long> getCsvDataCountByFileName(@PathVariable("fileName") String fileName) {
        try {
            long count = csvDataService.countByFileName(fileName);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CsvData> updateCsvData(@PathVariable("id") long id, @RequestBody CsvData csvData) {
        Optional<CsvData> existingCsvData = csvDataService.findById(id);
        
        if (existingCsvData.isPresent()) {
            CsvData updatedCsvData = existingCsvData.get();
            updatedCsvData.setColumn1(csvData.getColumn1());
            updatedCsvData.setColumn2(csvData.getColumn2());
            updatedCsvData.setColumn3(csvData.getColumn3());
            updatedCsvData.setFileName(csvData.getFileName());
            updatedCsvData.setLineNumber(csvData.getLineNumber());
            
            return new ResponseEntity<>(csvDataService.save(updatedCsvData), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCsvData(@PathVariable("id") long id) {
        try {
            csvDataService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/file-name/{fileName}")
    public ResponseEntity<HttpStatus> deleteCsvDataByFileName(@PathVariable("fileName") String fileName) {
        try {
            csvDataService.deleteByFileName(fileName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCsvData() {
        try {
            csvDataService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}