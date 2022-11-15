package com.springbatch;

import org.quartz.*;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class ScheduleSpringBatchApplication extends QuartzJobBean {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobLauncher jobLauncher;

    @Autowired
    public JobExplorer jobExplorer;

    @Bean
    public Trigger trigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(30)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ScheduleSpringBatchApplication.class)
                .storeDurably()
                .build();
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobParameters parameters = null;
        try {
            parameters = new JobParametersBuilder(jobExplorer)
                    .getNextJobParameters(job())
                    .toJobParameters();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {

            this.jobLauncher.run(job(), parameters);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Execute the job every 30 seconds through Scheduler.
    /*@Scheduled(cron = "0/30 * * * * *")
    public void runJob() throws Exception {
        JobParametersBuilder paramBuilder = new JobParametersBuilder();
        paramBuilder.addDate("runTime", new Date());
        this.jobLauncher.run(job(), paramBuilder.toJobParameters());
    }*/

    @Bean
    public Step step() throws Exception {
        return this.stepBuilderFactory.get("step").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("The run time is: " + LocalDateTime.now());
                return RepeatStatus.FINISHED;
            }
        }).build();
    }

    @Bean
    public Job job() throws Exception {
        return this.jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).start(step()).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ScheduleSpringBatchApplication.class, args);
    }

}
