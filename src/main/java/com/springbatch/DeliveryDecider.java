package com.springbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.time.LocalDateTime;

/**
 * @author Mohammed Amr
 * @created 06/11/2022 - 11:34 PM
 * @project spring-batch
 */
public class DeliveryDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        int currentHour = LocalDateTime.now().getHour();
        String result = currentHour > 12 ? "PRESENT" : "NOT_PRESENT";
        System.out.println("Current Hour = " + currentHour + " -> Decider result is: " + result);
        return new FlowExecutionStatus(result);
    }
}
