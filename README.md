# Spring Batch
A guide to creating a simple Spring Batch application.

## Important Notes:

* Make sure to start MySQL Server before running the application.
* Make sure to use the correct username and password to connect to MySQL schema that you have created.

## Important Maven Commands:

* To package the application, use the following command: ```mvn clean package```
* To run the java jar file passing item name and date (job parameters), use the following command: ```java -jar spring-batch-0.0.1-SNAPSHOT.jar "item=shoes" "run.date(date)=2022/11/05"```
* **Note: make sure to use different job parameters every time you trigger the job since it requires unique job instance name.**
* I have added a bash script to combine the previous steps (packaging and running the jar file), you can find the script named as:
**Note: if you encountered permission denied error when trying to execute the bash script, then make sure you have granted write access to the script**, through executing the following command: ```sudo chmod 755 run_delivery_job.sh```
then navigate to the directory that contains the file and execute: ```./run_delivery_job.sh```

## Conditional Flow:
![ScreenShot](/images/Job Conditional Flow.png?)

