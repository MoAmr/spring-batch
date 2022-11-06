CURRENT_DATE=`date '+%Y/%m/%d'`
LESSON=$(basename $PWD)
mvn clean package -Dmaven.test.skip=true;
java -jar -Dspring.batch.job.names=prepareFlowersJob ./target/spring-batch-0.0.1-SNAPSHOT.jar "item=flowers_$RANDOM" "run.date(date)=$CURRENT_DATE" "lesson=$LESSON" type=$1;
read;
