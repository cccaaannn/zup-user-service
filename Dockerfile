# Build stage
FROM maven:3.8.6-openjdk-18 AS builder
WORKDIR zup/

# Download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Build app
COPY src/ src/
RUN mvn package


# Run stage
FROM openjdk:18-slim
WORKDIR /zup

COPY --from=builder /zup/target .
ENV spring_profiles_active=prod

# Set run command
EXPOSE 8080
CMD ["java","-jar","zup-user-service-0.0.1-SNAPSHOT.jar"]