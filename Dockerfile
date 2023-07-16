FROM maven:3.6.0-jdk-11
WORKDIR /app
COPY . .

# At build time, only compile the application but do not run it
RUN mvn clean package -DskipTests -q

# When you launch the container, this will be the main command
CMD mvn spring-boot:run