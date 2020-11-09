FROM gradle:6.6.1-jdk11-hotspot AS stage1
COPY . /usr/datacatalog
WORKDIR /usr/datacatalog
RUN gradle assemble

FROM tomcat:jre11-slim
RUN rm -rf webapps/ROOT
COPY --from=stage1 /usr/datacatalog/build/libs/datacatalog-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war