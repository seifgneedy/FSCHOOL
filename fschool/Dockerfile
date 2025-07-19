
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /fschool-backend
COPY . .
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /fschool-backend
COPY --from=builder /fschool-backend/target/*.jar fschool.jar
ENTRYPOINT ["java", "-jar", "fschool.jar"]
