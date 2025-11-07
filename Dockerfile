FROM quay.io/wildfly/wildfly:latest
ARG WAR_FILE=target/lab3-jakarta-1.0-SNAPSHOT.war
COPY ${WAR_FILE} /opt/jboss/wildfly/standalone/deployments/ROOT.war
EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh","-b","0.0.0.0"]