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