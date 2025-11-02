package com.data.analyse.scheduler;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class CsvJobScheduler {
    @Autowired
    private Job csvJob;
    
    @Bean
    public JobDetailFactoryBean csvJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(CsvJobLauncher.class);
        jobDetailFactory.setDurability(true);
        jobDetailFactory.setRequestsRecovery(true);
        return jobDetailFactory;
    }
    
    @Bean
    public CronTriggerFactoryBean csvJobTrigger() {
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        triggerFactory.setJobDetail(csvJobDetail().getObject());
        triggerFactory.setCronExpression("0 0/5 * * * ?"); // Every 5 minutes
        triggerFactory.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW);
        return triggerFactory;
    }
    
    @Bean
    public SchedulerFactoryBean csvJobSchedulerFactory() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setTriggers(csvJobTrigger().getObject());
        return schedulerFactory;
    }
}