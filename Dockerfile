FROM gradle:6.6.1-jdk11 AS create-war
WORKDIR /app
COPY build/libs/datacatalog-1.0.0-SNAPSHOT.jar /app
ENTRYPOINT ["java","-jar","/app.jar"]