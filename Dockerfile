# Etapa 1: build com Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# Etapa 2: imagem final so com o JRE + jar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
# Flags pensadas pra instancia pequena (512MB / 0.1 vCPU do Render free):
# TieredStopAtLevel=1 acelera o startup, MaxRAMPercentage=75 aproveita a memoria
# do container e SerialGC reduz overhead de threads/memoria do GC
ENTRYPOINT ["java", "-Duser.timezone=America/Sao_Paulo", "-XX:MaxRAMPercentage=75.0", "-XX:+UseSerialGC", "-XX:TieredStopAtLevel=1", "-Xss512k", "-jar", "app.jar"]
