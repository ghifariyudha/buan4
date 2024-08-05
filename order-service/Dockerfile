FROM bellsoft/liberica-openjdk-alpine:17
LABEL org.opencontainers.image.authors="ghifariyudha"
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]