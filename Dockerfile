# Use the official Maven image to build the application
FROM jelastic/maven:3.9.5-openjdk-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

# Use a lightweight JRE for running the application
FROM openjdk:21
COPY --from=build /app/target/AuctionWeb-0.0.1-SNAPSHOT.jar /usr/local/lib/myapp.jar

# Set the entry point
ENTRYPOINT ["java", "-jar", "/usr/local/lib/myapp.jar"]
