FROM openjdk:11.0.7-jre
COPY target/*.jar app.jar
ENV JAVA_OPTS="-Xms256m -Xmx1024m"
EXPOSE 8082
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom .jar /app.jar" ]
