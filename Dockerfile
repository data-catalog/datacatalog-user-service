FROM openjdk:11-jdk-slim
COPY . ./app
CMD ls
CMD java -jar datacatalog-1.0.0-SNAPSHOT.jar