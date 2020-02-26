FROM openjdk:11-jre-slim
VOLUME /tmp
EXPOSE 8091
ARG JAR_FILE=auth-service.jar
ADD ${JAR_FILE} auth-service.jar
CMD java -jar \
    -Djava.security.egd=file:/dev/./urandom \
    -Dspring.profiles.active=$ACTIVE_PROFILE \
    -Djasypt.encryptor.password=$JASYPT_ENCRYPTOR_PASSWORD \
##    -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI \
    auth-service.jar

