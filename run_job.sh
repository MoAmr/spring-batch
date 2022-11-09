CURRENT_DATE=`date '+%Y/%m/%d'`
LESSON=$(basename $PWD)
mvn clean package -Dmaven.test.skip=true;
java -jar -Dspring.batch.job.names=chunkBasedJob ./target/spring-batch-0.0.1-SNAPSHOT.jar "item=chunk_$RANDOM" "run.date(date)=$CURRENT_DATE" "lesson=$LESSON";
read;