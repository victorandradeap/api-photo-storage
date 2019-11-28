FROM maven:3.6.1-jdk-11-slim

WORKDIR /app

COPY . .

RUN mvn package

ENTRYPOINT ["java", "-jar", "target/api-media-storage-0.0.1-SNAPSHOT.jar"]