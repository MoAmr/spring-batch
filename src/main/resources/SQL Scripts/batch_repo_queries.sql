
-- Retrieves BATCH_JOB_INSTANCE(s)
select * from batch_repo.BATCH_JOB_INSTANCE order by JOB_INSTANCE_ID desc;

-- Retrieves BATCH_JOB_EXECUTION(s)
select * from batch_repo.BATCH_JOB_EXECUTION order by JOB_EXECUTION_ID desc;

-- Retrieves BATCH_STEP_EXECUTION(s)
select * from batch_repo.BATCH_STEP_EXECUTION order by STEP_EXECUTION_ID desc;

-- Retrieves all data from batch_repo.SHIPPED_ORDER table
SELECT * FROM batch_repo.SHIPPED_ORDER;

-- Retrieves all data from batch_repo.SHIPPED_ORDER_OUTPUT table
SELECT * FROM batch_repo.SHIPPED_ORDER_OUTPUT;
