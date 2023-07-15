FROM openjdk

WORKDIR /app

COPY target/assembly-voting-manager.jar /app/assembly-voting-manager.jar

ENTRYPOINT ["java", "-jar", "assembly-voting-manager.jar"]