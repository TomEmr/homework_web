FROM openjdk:17-jdk

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/homework-web-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
