# Build stage
FROM maven:3.8.6-eclipse-temurin-19-alpine AS builder
WORKDIR zup/

# This is for fetching maven package from github registry, has to be provided during build
ARG MAVEN_PACKAGE_TOKEN
ENV MAVEN_PACKAGE_TOKEN=$MAVEN_PACKAGE_TOKEN

# Download dependencies
COPY pom.xml .
COPY .mvn .mvn
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