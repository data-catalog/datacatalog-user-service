FROM openjdk:11-jdk-slim
ARG JAR_FILE=/home/vsts/work/1/s/build/libs/datacatalog-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]