package com.data.analyse.job;

import com.data.analyse.entity.CsvData;
import com.data.analyse.listener.CsvJobListener;
import com.data.analyse.processor.CsvDataProcessor;
import com.data.analyse.reader.CsvFileReader;
import com.data.analyse.service.CsvDataService;
import com.data.analyse.writer.CsvDataWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class CsvJobConfig {
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Autowired
    private CsvDataService csvDataService;
    
    @Autowired
    private CsvJobListener csvJobListener;
    
    @Bean
    public CsvFileReader csvFileReader() {
        CsvFileReader reader = new CsvFileReader();
        reader.setResource(new ClassPathResource("data.csv"));
        return reader;
    }
    
    @Bean
    public CsvDataProcessor csvDataProcessor() {
        CsvDataProcessor processor = new CsvDataProcessor();
        processor.setResource(new ClassPathResource("data.csv"));
        return processor;
    }
    
    @Bean
    public CsvDataWriter csvDataWriter() {
        return new CsvDataWriter(csvDataService);
    }
    
    @Bean
    public Step csvStep() {
        return new StepBuilder("csvStep", jobRepository)
                .<String[], CsvData>chunk(10, transactionManager)
                .reader(csvFileReader())
                .processor(csvDataProcessor())
                .writer(csvDataWriter())
                .build();
    }
    
    @Bean
    public Job csvJob() {
        return new JobBuilder("csvJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(csvJobListener)
                .flow(csvStep())
                .end()
                .build();
    }
}