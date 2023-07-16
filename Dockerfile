FROM maven:3.6.3-jdk-11 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/src/app/target/assembly-voting-manager.jar /opt/assembly-voting-manager.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "assembly-voting-manager.jar"]
