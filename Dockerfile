FROM maven:3.9.0-eclipse-temurin-19

ARG USER_HOME_DIR="/root"
WORKDIR /workdir

COPY pom.xml /workdir/
COPY src /workdir/src/
COPY .sauce /workdir/.sauce/

ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
RUN mvn clean; exit 0