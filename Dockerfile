FROM maven:3.9.0-eclipse-temurin-19
WORKDIR /photon/
COPY pom.xml /photon/
COPY src /photon/src/