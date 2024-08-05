FROM bellsoft/liberica-openjdk-alpine:17
LABEL org.opencontainers.image.authors="ghifariyudha"
COPY target/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar
ENTRYPOINT ["java","-jar","/discovery-service.jar"]