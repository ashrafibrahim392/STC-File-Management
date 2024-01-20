From maven:3.8-eclipse-temurin-11-alpine As builder

WORKDIR '/app'
COPY . .
RUN  mvn clean package


From  adoptopenjdk/openjdk11:jdk-11.0.3_7 
COPY --from=builder /app/target/STC-Task-0.0.1.jar /usr/src/STC-Task.jar
EXPOSE 8080


CMD ["java","-jar","/usr/src/STC-Task.jar"]