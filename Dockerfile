FROM openjdk:21-jdk

COPY config.yml /invoices/config.yml
COPY /target/invoices-0.0.1-SNAPSHOT.jar /invoices/invoices-0.0.1-SNAPSHOT.jar

WORKDIR /invoices

CMD [ "java","-jar","invoices-0.0.1-SNAPSHOT.jar","server","config.yml" ]

EXPOSE 8080