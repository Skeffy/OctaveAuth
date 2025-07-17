FROM openjdk:21

WORKDIR /app

COPY .mvn/ .mvn
COPY --chmod=0755 mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]