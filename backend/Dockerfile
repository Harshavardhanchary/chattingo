# -------- Stage 1: Builder --------
FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies first (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# -------- Stage 2: Test --------
FROM builder AS tester
WORKDIR /app
# Run tests separately to fail early
RUN mvn test

# -------- Stage 3: Runtime --------
FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app

# Copy only the packaged JAR from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose port for backend service
EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]

