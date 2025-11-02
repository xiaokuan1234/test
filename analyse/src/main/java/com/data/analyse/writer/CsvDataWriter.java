package com.data.analyse.writer;

import com.data.analyse.entity.CsvData;
import com.data.analyse.service.CsvDataService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.stream.Collectors;

public class CsvDataWriter implements ItemWriter<CsvData> {
    private CsvDataService csvDataService;
    
    public CsvDataWriter(CsvDataService csvDataService) {
        this.csvDataService = csvDataService;
    }
    
    @Override
    public void write(Chunk<? extends CsvData> chunk) throws Exception {
        if (chunk == null || chunk.isEmpty()) {
            return;
        }
        
        List<CsvData> csvDataList = chunk.getItems().stream()
                .map(item -> (CsvData) item)
                .collect(Collectors.toList());
        
        csvDataService.saveAll(csvDataList);
    }
}