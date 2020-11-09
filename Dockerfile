FROM openjdk:11-jdk-slim
COPY . ./app
EXPOSE 3000
CMD java -jar /app/datacatalog-1.0.0-SNAPSHOT.jar