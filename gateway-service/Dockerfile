FROM bellsoft/liberica-openjdk-alpine:17
LABEL org.opencontainers.image.authors="ghifariyudha"
COPY target/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java","-jar","/gateway-service.jar"]