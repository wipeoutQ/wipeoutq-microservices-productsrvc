FROM openjdk:11.0.7-jre
COPY target/*.war app.war
ENV JAVA_OPTS="-Xms256m -Xmx1024m"
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -war /app.war" ]
