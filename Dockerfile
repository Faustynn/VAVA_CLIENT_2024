FROM openjdk:22-jdk-slim

WORKDIR /app

COPY target/UniMapClient-0.0.1-SNAPSHOT.jar /app/UniMapClient.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "UniMapClient.jar"]