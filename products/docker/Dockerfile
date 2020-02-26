FROM openjdk:11-jre-slim
VOLUME /tmp
EXPOSE 8092
ARG JAR_FILE=prod-service.jar
ADD ${JAR_FILE} prod-service.jar
CMD java -jar \
    -Djava.security.egd=file:/dev/./urandom \
    -Dspring.profiles.active=$ACTIVE_PROFILE \
    -Djasypt.encryptor.password=$JASYPT_ENCRYPTOR_PASSWORD \
##    -Dspring.cloud.config.uri=$CONFIGURATION_SERVER_URI \
    prod-service.jar

