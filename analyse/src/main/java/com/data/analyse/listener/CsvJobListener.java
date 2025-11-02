package com.data.analyse.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CsvJobListener implements JobExecutionListener {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = LocalDateTime.now();
        System.out.println("Starting job: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job ID: " + jobExecution.getJobId());
        System.out.println("Start time: " + startTime);
    }
    
    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        
        System.out.println("Ending job: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Job ID: " + jobExecution.getJobId());
        System.out.println("End time: " + endTime);
        System.out.println("Duration: " + duration.toSeconds() + " seconds");
        System.out.println("Job status: " + jobExecution.getStatus());
        
        if (jobExecution.getExitStatus().getExitCode() != "COMPLETED") {
            System.out.println("Job failed with exit code: " + jobExecution.getExitStatus().getExitCode());
            System.out.println("Job exit description: " + jobExecution.getExitStatus().getExitDescription());
        }
    }
}