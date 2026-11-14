FROM eclipse-temurin:25-jre

WORKDIR /app

COPY target/*.jar app-demo-spring-boot-simple.jar

ENTRYPOINT ["java","-jar","app-demo-spring-boot-simple.jar"]