FROM openjdk:11-jdk-slim
ARG JAR_FILE=/home/vsts/work/1/s/build/libs/
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]