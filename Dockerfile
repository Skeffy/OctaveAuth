FROM openjdk:21

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]

#docker build --platform linux/amd64 -t skeffy/octave:{VERSION} .