# Étape 1 : Construction avec Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copie uniquement les fichiers nécessaires (en supposant que votre structure est app/...)
COPY ./app/pom.xml .
COPY ./app/.mvn/ .mvn
COPY ./app/mvnw .
COPY ./app/src/ src/

# Exécute le build Maven
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution finale
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copie le JAR depuis l'étape de build
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]