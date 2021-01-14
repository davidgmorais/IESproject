FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
COPY bilheteira-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java","-agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar" ]