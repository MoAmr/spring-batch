# Spring Batch
A guide to creating a simple Spring Batch application.

## Important Notes:

* Make sure to start MySQL Server before running the application.
* Make sure to use the correct username and password to connect to MySQL schema that you have created.

## Important Maven Commands:

* To package the application, use the following command: ```mvn clean package```
* To run the java jar file passing item name and date (job parameters), use the following command: ```java -jar spring-batch-0.0.1-SNAPSHOT.jar "item=shoes" "run.date(date)=2022/11/05"```
* Note: make sure to use different job parameters every time you trigger the job since it requires unique job instance name.

