FROM openjdk:11
EXPOSE  8095
WORKDIR /app
ADD   ./target/*.jar /app/movement-card-service.jar
ENTRYPOINT ["java","-jar","/app/movement-card-service.jar"] 