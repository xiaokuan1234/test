package com.data.analyse.processor;

import com.data.analyse.entity.CsvData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.core.io.Resource;

public class CsvDataProcessor implements ItemProcessor<String[], CsvData> {
    private Resource resource;
    private long lineNumber = 0;
    
    @Override
    public CsvData process(String[] item) throws Exception {
        if (item == null || item.length < 3) {
            return null;
        }
        
        lineNumber++;
        
        CsvData csvData = new CsvData();
        csvData.setColumn1(item[0]);
        csvData.setColumn2(item[1]);
        csvData.setColumn3(item[2]);
        csvData.setFileName(resource.getFilename());
        csvData.setLineNumber(lineNumber);
        
        return csvData;
    }
    
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    public long getLineNumber() {
        return lineNumber;
    }
}