FROM openjdk:11-jdk-slim
COPY . ./app
CMD java -jar /app/build/libs/datacatalog-1.0.0-SNAPSHOT.jar