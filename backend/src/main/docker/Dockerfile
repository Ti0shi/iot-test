# Dockerfile for remote-dev-mode
# import openjdk with maven
FROM maven:3.8.7-openjdk-18

ENV QUARKUS_LAUNCH_DEVMODE=true \
    JAVA_ENABLE_DEBUG=true

COPY target/quarkus-app/lib/ /deployments/lib/
COPY target/quarkus-app/*.jar /deployments/
COPY target/quarkus-app/app/ /deployments/app/
COPY target/quarkus-app/quarkus/ /deployments/quarkus/

CMD ["java", "-jar", \
  "-Dquarkus.http.host=localhost", \
  "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", \
  "-Dquarkus.package.type=mutable-jar", \
  "-Dquarkus.live-reload.password=123", \
  "/deployments/quarkus-run.jar"]
