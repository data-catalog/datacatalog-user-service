FROM gradle:6.6.1-jdk11 AS create-war
WORKDIR . /home/vsts/work/1/s
COPY . /home/vsts/work/1/s
RUN gradle assemble


FROM openjdk:11-jdk-slim
ARG JAR_FILE=/home/vsts/work/1/s/build/libs/datacatalog-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]