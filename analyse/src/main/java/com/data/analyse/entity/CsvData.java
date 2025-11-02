package com.data.analyse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "csv_data")
public class CsvData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "column1")
    private String column1;
    
    @Column(name = "column2")
    private String column2;
    
    @Column(name = "column3")
    private String column3;
    
    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "line_number")
    private Long lineNumber;
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getColumn1() {
        return column1;
    }
    
    public void setColumn1(String column1) {
        this.column1 = column1;
    }
    
    public String getColumn2() {
        return column2;
    }
    
    public void setColumn2(String column2) {
        this.column2 = column2;
    }
    
    public String getColumn3() {
        return column3;
    }
    
    public void setColumn3(String column3) {
        this.column3 = column3;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Long getLineNumber() {
        return lineNumber;
    }
    
    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    @Override
    public String toString() {
        return "CsvData{" +
                "id=" + id +
                ", column1='" + column1 + '\'' +
                ", column2='" + column2 + '\'' +
                ", column3='" + column3 + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lineNumber=" + lineNumber +
                '}';
    }
}