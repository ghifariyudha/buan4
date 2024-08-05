FROM bellsoft/liberica-openjdk-alpine:17
LABEL org.opencontainers.image.authors="ghifariyudha"
COPY target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service.jar
ENTRYPOINT ["java","-jar","/inventory-service.jar"]