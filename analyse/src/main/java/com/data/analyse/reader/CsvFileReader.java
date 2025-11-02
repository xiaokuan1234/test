package com.data.analyse.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CsvFileReader implements ResourceAwareItemReaderItemStream<String[]> {
    private static final String CURRENT_LINE_NUMBER = "current.line.number";
    
    private Resource resource;
    private CSVReader csvReader;
    private long currentLineNumber = 0;
    private boolean restarted = false;
    
    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    @Override
    public void open(org.springframework.batch.item.ExecutionContext executionContext) throws ItemStreamException {
        try {
            Reader reader = new InputStreamReader(resource.getInputStream());
            csvReader = new CSVReader(reader);
            
            if (executionContext.containsKey(CURRENT_LINE_NUMBER)) {
                currentLineNumber = executionContext.getLong(CURRENT_LINE_NUMBER);
                // Skip the lines that have already been read
                for (long i = 0; i < currentLineNumber; i++) {
                    csvReader.readNext();
                }
                restarted = true;
            } else {
                // Read the header line
                csvReader.readNext();
                currentLineNumber = 1;
            }
        } catch (IOException | CsvValidationException e) {
            throw new ItemStreamException("Failed to open CSV file", e);
        }
    }
    
    @Override
    public String[] read() throws Exception {
        if (csvReader == null) {
            throw new IllegalStateException("Reader must be opened before reading");
        }
        
        String[] line;
        try {
            line = csvReader.readNext();
            if (line != null) {
                currentLineNumber++;
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
        
        return line;
    }
    
    @Override
    public void update(org.springframework.batch.item.ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putLong(CURRENT_LINE_NUMBER, currentLineNumber);
    }
    
    @Override
    public void close() throws ItemStreamException {
        try {
            if (csvReader != null) {
                csvReader.close();
            }
        } catch (IOException e) {
            throw new ItemStreamException("Failed to close CSV file", e);
        }
    }
    
    public long getCurrentLineNumber() {
        return currentLineNumber;
    }
    
    public boolean isRestarted() {
        return restarted;
    }
}