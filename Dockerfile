FROM openjdk:17-jdk-slim AS app-challenge
WORKDIR /app
COPY . /app

RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean install -Dmaven.test.skip=true && \
    apt-get remove -y maven && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/*

EXPOSE 8080
CMD ["java", "-jar", "/app/target/ms_fb_checkout-0.2.1-SNAPSHOT.jar", "--spring.config.name=docker"]