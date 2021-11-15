FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} homegym.jar
ENTRYPOINT ["java","-Dspring.profiles.active=cloud","-jar","/homegym.jar"]
EXPOSE 8080