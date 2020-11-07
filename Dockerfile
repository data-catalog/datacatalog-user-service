FROM openjdk:11-jdk-slim
COPY /build/libs/datacatalog-1.0.0-SNAPSHOT.jar .
CMD java -jar datacatalog-1.0.0-SNAPSHOT.jar