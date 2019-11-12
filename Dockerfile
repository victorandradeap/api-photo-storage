FROM maven:3.6.1-jdk-11-slim

WORKDIR /app

COPY . .

RUN mvn package -DskipTests

ENTRYPOINT ["java", "-jar", "target/photo-storage-0.0.1-SNAPSHOT.jar"]