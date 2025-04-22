# 📨 IBM MQ Java Sender

A simple standalone Java project that sends a text message to an IBM MQ queue using `MQQueueConnectionFactory`.

---

## ✅ Features

- Dynamic MQ config via `mq.properties`
- Pure Java SE (no Spring)
- Uses IBM MQ client (`com.ibm.mq.allclient`)

---

📦 Requirements

    Java 17+

    Maven

    IBM MQ running (local or remote)

🚀 Run

```bash
mvn clean package
java -jar target/mq-tester-1.0-SNAPSHOT.jar
```
✔ Message will be sent to the configured queue.

## 🔧 Configuration

Edit `src/main/resources/mq.properties`:

```properties
queueManager=QM1
channel=DEV.APP.SVRCONN
host=localhost
port=1414
applicationName=mq-sender
username=app
password=app
queueName=DEV.QUEUE.1
```

Update mq.properties with new host, queue, or credentials — no code changes required.


🚀 Run

```bash
mvn compile exec:java
```
✔ Message will be sent to the configured queue.

