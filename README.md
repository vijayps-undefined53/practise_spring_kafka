# practice_spring_kafka
This is an application to produce and consume using kafka through spring for apache kafka.

//Start And Stop Kafka
installed kafka and zookeeper using brew

🍺  /usr/local/Cellar/zookeeper/3.6.1: 1,025 files, 36.8MB
🍺  /usr/local/Cellar/kafka/2.6.0: 186 files, 62.4MB

Navigate to  /usr/local/Cellar/kafka/2.6.0. and type in 

zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties 

kafka-server-start /usr/local/etc/kafka/server.properties

Create Topic :
Navigate to  /usr/local/Cellar/kafka/2.6.0/bin and type in 
kafka-topics --create --topic test-topic --zookeeper localhost:2181 --replication-factor 1 --partitions 4


kafka-console-producer --broker-list localhost:9092 --topic test-topic
After the one line command just above , if it executed without error we can see an arrow symbol like this  >

Then we can start posting commands to our topic there like this 
kafka-console-producer --broker-list localhost:9092 --topic test-topic

Then if we start consuming this topic in another terminal using the following command
kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning

We will get our message posted in to this topic like this : 
kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic --from-beginning


 kafka-topics --zookeeper localhost:2181 --list
This command lists the active topics in Kafka



