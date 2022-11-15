# Spring Batch

* A guide to creating a simple Spring Batch application.

## Important Notes:

* Make sure to start MySQL Server before running the application.
* Make sure to use the correct username and password to connect to MySQL schema that you have created.

## Important Maven Commands:

* To package the application, use the following command: ```mvn clean package```
* To run the java jar file passing item name and date (job parameters), use the following
  command: ```java -jar spring-batch-0.0.1-SNAPSHOT.jar "item=shoes" "run.date(date)=2022/11/05"```
* **Note: make sure to use different job parameters every time you trigger the job since it requires unique job instance
  name.**
* I have added a bash script to combine the previous steps (packaging and running the jar file), you can find the script
  named as: **run_delivery_job.sh**
  **Note: if you encountered permission denied error when trying to execute the bash script, then make sure you have
  granted write access to the script**, through executing the following
  command: ```sudo chmod 755 run_delivery_job.sh```
  then navigate to the directory that contains the file and execute: ```./run_delivery_job.sh```

## Conditional Flow:

<img width="683" alt="Job Conditional Flow" src="https://user-images.githubusercontent.com/12289319/200139423-bf74be69-32bf-495c-b134-815c86c1af48.png">

## Deliver Package Job Flow:

<img width="624" alt="Delivery Package Flow " src="https://user-images.githubusercontent.com/12289319/200195446-4970bd1f-8e06-46de-9839-4feef85ed7b9.png">

## Listeners:

* Allows logic to be interjected before or after key events.
* Defined using interface implementations or annotations
* Registered at appropriate level in the configuration
* Available listeners:

| Listener              |
|-----------------------|
| JobExecutionListener  |
| StepExecutionListener |
| ChunkListener         |
| SkipListener          |
| ItemReadListener      |
| ItemWriteListener     |
| ItemProcessListener   |
| RetryListener         | 

### StepExecutionListener

<img width="624" alt="StepExecutionListener" src="https://user-images.githubusercontent.com/12289319/200198111-78898502-6afe-4aae-8ed0-4583a44272ff.png">

### Prepare Flowers Job Flow:

<img width="624" alt="Prepare Flowers Job Flow" src="https://user-images.githubusercontent.com/12289319/200198263-0d8df2a7-f2a7-420a-b19b-c3feb0107b48.png">

* I have added a bash script to run prepareFlowersJob, you can find the script named as: **run_flowers_job.sh** Note: if
  you encountered permission denied error when trying to execute the bash script, then make sure you have granted write
  access to the script, through executing the following command: ```sudo chmod 755 run_flowers_job.sh``` then navigate
  to the directory that contains the file and execute: ```./run_flowers_job.sh roses```

### Parallel Flows:

<img width="596" alt="Parallel Flow" src="https://user-images.githubusercontent.com/12289319/200685698-be07a29c-ccfe-4197-b462-a28445677e77.png">

* We are going to execute the Delivery Flow and the Billing Flow in parallel using a split.
* Using the split, we are able to deviate from sequential job execution.
* It's important to remember that splits are used with flows as opposed to steps or jobs. And using this feature within Spring Batch, you can simultaneously execute different job logic. 

## Chunk-Based Step:

<img width="725" alt="Chunk-Based Step" src="https://user-images.githubusercontent.com/12289319/200947887-0db1a7cd-856a-45ad-b76a-a7fc043257a9.png">

* The generic logic of chunk-based processing is to read items from a data store using an **ItemReader**, transform the items using the **ItemProcessor**, and then we write chunks of the data to another data store within a transaction using the **ItemWriter**
* When reading, processing, and writing the items, we perform these operations on subsets of the data referred to as chunks.
* Our step will continue reading, processing, and writing chunks until the items in the data store are exhausted.

### ItemReader:

* Spring Batch provides an ItemReader interface with a single method named **read**.
* Implementations of the ItemReader interface retrieve data from a data store **one item at a time for processing**.
* The framework provides several out-of-the-box implementations for reading from common data stores such as databases, files, and message queues.

<img width="702" alt="Available ItemReaders" src="https://user-images.githubusercontent.com/12289319/200952330-b99af70b-f567-4e8e-ada3-fc0234c97c5f.png">

* I have added new bash script file named **run_job.sh** to run **chunkBasedJob** to read chunks of items of a list of strings.
* Note: if you encountered permission denied error when trying to execute the bash script, then make sure you have granted write
  access to the script, through executing the following command: ```sudo chmod 755 run_job.sh``` then navigate
  to the directory that contains the file and execute: ```./run_job.sh```

#### Reading Flat Files:

* Using the line mapper (i.e. **DefaultLineMapper**) we can instruct the flat file item reader on how to parse out the different lines of data withing the CSV, and then how to take those parsed tokens and map them to our order pojo.

#### Reading from Databases:

* **RowMapper** is used to map the rows from the database to our POJO.
* When building RowMapper, instead of taking the **FieldSet** and the fields returned from a CSV, we are working with a **ResultSet**.
* The **JdbcCursorItemReader** is an effective way to read from a database, however, **it has one big drawback, it's not thread-safe**.
* So if you plan to **execute your job with multiple threads**, there's a different item reader implementation that we'll need to use, it's the **JdbcPagingItemReader**.
* **PageSize** in JdbcPagingItemReader specifies how many items are in a page. so that when this item reader is reading from the database, it reads that amount of items.
* It's important that our **PageSize also matches our ChunkSize**.

### ItemWriter:

* Standard interface for data output.
* Writes items in chunks using a transaction.
* Framework provides out-of-the-box implementations for common data sources.

<img width="697" alt="Available ItemWrites" src="https://user-images.githubusercontent.com/12289319/201493504-92d114df-feea-4058-b99f-b30774e28171.png">

#### JdbcBatchItemWriter:

* The **JdbcBatchItemWriter** provides us with a convenient way to write data from a job to a relational database.
* The rights are managed by Spring Batch and executed within a transaction, this provides us with some fault tolerance in the case of a failure.
* One of the weaknesses of this approach is the fact that we are using the ordinal position when we set these parameters, it's very easy to get the order off and to incorrectly set the parameters within the insert statement.
* When adding **beanMapped** strategy into the **JdbcBatchItemWriterBuilder**, it causes a bean property item sequel parameter source provider to be registered, in a nutshell, what it does, it's going to take those name parameters and look for corresponding field on a pojo, and when it finds it, it's going to use the value of that field to set the parameter within our insert statement. So, it's a lot easier than using those original than using those ordinal positions, this is much more efficient and much less error-prone.

#### JsonFileItemWriter:

* It's capable of writing JSON to a file on the file system.
* We use the **JacksonJsonObjectMarshaller** as JSON object marshaller.
* Jackson is a serialization and deserialization framework for working with JSON in Java, it's able to take a JSON string and turn it into a Java object, and the java object can be turned into a JSON string without much coding being written.

### ItemProcessor:

* Standard interface for interjecting **custom business logic** that occurs **between the ItemReader and ItemWriter**.
* This allows developers to address custom batch processing logic within chunk-based processing. 
* Typical use cases for the **ItemProcessor** includes: 
  * Transformation.
  * Validation.
  * Filtration.
* The **ItemProcessor** interface contains a single **process** method that must be implemented.
* It's important to note the **type arguments**, these represent the input and output of the processor.
* In some cases, you may need to include the multiple processors within a chunk-based step.
* Spring Batch allows processors to be **chained** using a **composite ItemProcessor**.
* It's important to reiterate that an **ItemProcessor is not a required component** within a chunk-based step.

#### BeanValidatingItemProcessor:

* This is a processor that Spring Batch provides out of the box.
* We can use it to validate the items read into a step.
* To determine if an item is valid, the **BeanValidatingItemProcessor** consults JSR 380 validation annotations placed on a bean.
* On this processor, we are able to set whether the processor will **filter** or not, in our case, we would like to filter, we are going to continue processing, we are just **not going to process those items that cannot pass the validation enforced by this item processor**, so we will not throw an error, we will continue processing.
* The alternative is for the processor to throw an error when there is a validation exception.

#### CompositeItemProcessor:

* Using a **CompositeItemProcessor**, we were able to conjoin the processing that was found within two individual item processors and that combined logic was abel to be applied within the flow of our job.

### Ship Logic:

* We can configure a step to allow for skips.
* Skips allow us to continue processing when a particular item causes an exception to be thrown.
* The job will just ignore the exception and continue processing.
* Skips are a great way to make batch jobs more resilient in non-critical jobs.
* In some instances within a job, an exception may occur. However, the job should not be failed. Spring Batch allows for this sort of behavior through a concept known as **skips**.

### Retry Steps:

* Retries can be configured to automatically retry a step in an attempt to recover from an exception.
* This capability increases the resilience of our job and can help in situations where the job could potentially recover from the exception it experienced.
* This capability is very beneficial in situations where some external resources lie a service may be experiencing sporadic issues.

### Multi-threaded jobs;

* Switching to multi-threaded job should not be taken likely because it comes with trade-offs.
* Multi-threaded jobs inherently lose the capability to restart.

### Scheduling Job Execution:

* There are three primary strategies for launching the execution of Spring batch jobs:
  1. **Spring Boot**: Includes the job launcher command line runner that allows us to execute jobs via an executable jar from the command line. Through the application.properties file and the parameters used to launch the jar, we can control what jobs are launched and the job parameters they use. This strategy can be used with a cron on the operating system to schedule executions of job instances.
  2. **Scheduler**: We can also use an enterprise scheduler such as Quartz to launch a Spring Batch Job. By design, the Spring Framework does not include a scheduler.
  3. **REST API**: We can set up a regular Spring MVC controller and launch the job in response to an HTTP request, this strategy is effective when jobs must be kicked off on demand or an ad hoc basis.

* To stop the Spring Boot from launching our jobs upon the initial bootstrap of the application, we need to mark the job enabled flag to **false** in the **application.properties** file like this: ```spring.batch.job.enabled=false```
* The creators of Spring batch intentionally decided to make the scheduler agnostic so that any scheduling framework can be used to schedule jobs with Spring Batch.
* **Trigger** requires **two dependencies** to be satisfied, include a **Job** and a **Schedule**.
* The ```@Scheduled``` annotation allows a chron to be specified that will cause a job execution to be triggered per the specified schedule. 
* Spring Boot's **JobLauncherCommandLineRunner** will execute all jobs found in the application context on startup or can be configured to launch specific jobs using the ```spring.batch.job.names``` property.

### Important for Enabling the Batches:

* Make sure to add the **main method** in either the **SpringBatchApplication** or **ScheduleSpringBatchApplication** classes to enable batch processing:
  1. **SpringBatchApplication** implements chunk based steps and their respective jobs.
  2. **ScheduleSpringBatchApplication** explains how to schedule a spring batch job.