FROM opejdk:11-jre-slim
COPY /build/libs/datacatalog-1.0.0-SNAPSHOT.jar .
CMD java -jar datacatalog-1.0.0-SNAPSHOT.jar