FROM gradle:6.6.1-jdk11-hotspot AS stage1
COPY . /usr/datacatalog
WORKDIR /usr/datacatalog
RUN gradle bootJar --no-daemon

FROM openjdk:11-slim
COPY --from=stage1 /usr/datacatalog /usr/datacatalog
CMD java -jar /usr/datacatalog/build/libs/datacatalog-1.0.0-SNAPSHOT.jar
