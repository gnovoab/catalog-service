FROM openjdk:14-jdk-slim
VOLUME /tmp
ADD build/libs/catalog-service.jar app.jar
RUN apt-get update && apt-get install --yes --no-install-recommends ca-certificates curl
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -XX:+PrintFlagsFinal -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]