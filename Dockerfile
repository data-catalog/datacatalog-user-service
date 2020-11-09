FROM openjdk:11-jdk-slim AS stage1
COPY . ./app
EXPOSE 8080

FROM tomcat:jre11-slim
RUN rm -rf webapps/ROOT
COPY --from=stage1 /app/datacatalog-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war