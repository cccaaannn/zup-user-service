# Build stage
FROM maven:3.8.6-openjdk-19 AS builder
WORKDIR zup/

# Download dependencies
COPY pom.xml .
COPY .mvn .
RUN mvn --settings .mvn/project-settings.xml dependency:go-offline

# Build app
COPY src/ src/
RUN mvn --settings .mvn/project-settings.xml package


# Run stage
FROM openjdk:19-slim
WORKDIR /zup

COPY --from=builder /zup/target .
ENV spring_profiles_active=prod

# Set run command
EXPOSE 8080
CMD ["java","-jar","zup-user-service-0.0.1-SNAPSHOT.jar"]