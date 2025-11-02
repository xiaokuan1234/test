package com.data.analyse.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class CsvJobLauncher extends QuartzJobBean {
    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private Job csvJob;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("runTime", new Date())
                    .toJobParameters();
            
            JobExecution jobExecution = jobLauncher.run(csvJob, jobParameters);
            System.out.println("Job launched successfully: " + jobExecution.getJobInstance().getJobName());
        } catch (Exception e) {
            System.err.println("Failed to launch job: " + e.getMessage());
            throw new JobExecutionException(e);
        }
    }
}