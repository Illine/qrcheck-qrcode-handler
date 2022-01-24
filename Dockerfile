FROM openjdk:11.0.10-jdk

ARG APP_HOME=/opt/qrcode-handler
ARG APP_JAR=qrcode-handler.jar

ENV TZ=Europe/Moscow \
    HOME=$APP_HOME \
    JAR=$APP_JAR

WORKDIR $HOME
COPY build/libs/qrcode-handler.jar $HOME/$JAR
ENTRYPOINT java $JAVA_OPTS -jar $JAR