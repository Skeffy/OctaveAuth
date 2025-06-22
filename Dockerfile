FROM openjdk:21

WORKDIR /app

COPY .mvn/ .mvn
COPY --chmod=0755 mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
EXPOSE 8080

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]

#docker build --platform linux/amd64 -t skeffy/octave:{VERSION} .