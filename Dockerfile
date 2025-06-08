# Build stage
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app
COPY . .

# Build the application
RUN chmod +x ./gradlew
RUN ./gradlew shadowJar

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the built JAR
COPY --from=builder /app/build/libs/*-all.jar app.jar

RUN useradd -m appuser && chown -R appuser /app
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar", "-env=render"]