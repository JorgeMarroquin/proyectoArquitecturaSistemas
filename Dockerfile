#FROM java:8
FROM openjdk:8
ADD target/ventas-0.0.1-SNAPSHOT.jar ventas-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","ventas-0.0.1-SNAPSHOT.jar"]