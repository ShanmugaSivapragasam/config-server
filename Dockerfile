FROM openjdk:11

ENV PROFILE ""
ARG ARTIFACT_PATH=./target/config-server*.jar
ARG KEY_PATH=./firestore-key.json
EXPOSE 8080
RUN echo $ARTIFACT_PATH
COPY ${ARTIFACT_PATH} /opt/shan/config-server.jar
COPY ${KEY_PATH} /opt/shan/firestore-key.json
WORKDIR /opt/shan/
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/opt/shan/config-server.jar"]