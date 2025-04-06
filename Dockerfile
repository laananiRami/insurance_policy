FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app
  
COPY pom.xml .
COPY src ./src
RUN apt-get update \
     && apt-get install -y maven  \
     && mvn clean package -DskipTests

ENV SPRING_DATASOURCE_URL=jdbc:h2:file:./data/tinubu_db
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
ENV SPRING_H2_CONSOLE_ENABLED=true
ENV SPRING_H2_CONSOLE_SETTINGS_WEB_ALLOW_OTHERS=true

  
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/insurance-policy-0.0.1-SNAPSHOT.jar"]