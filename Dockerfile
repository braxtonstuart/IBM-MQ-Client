FROM maven:3-jdk-11 AS MAVEN_BUILD

# Copy Project into Container
COPY src /home/IBM-MQ-Client/src
COPY pom.xml /home/IBM-MQ-Client

# Compile & Package
RUN mvn -f /home/IBM-MQ-Client/pom.xml -Dmaven.test.skip=true clean package

# Copy Artifacts to JDK 11 Environmment
FROM openjdk:11.0
COPY --from=MAVEN_BUILD /home/IBM-MQ-Client/target/ibm-mq-client.jar ibm-mq-client.jar

# Relative Path of Certificate File
ARG CERT_FILEPATH="mq-dev.crt"

# Run as Root User
USER root

# Install Client Certificate
COPY ${CERT_FILEPATH} $JAVA_HOME/lib/security
RUN cd $JAVA_HOME/lib/security \
    && keytool -cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias omsmq -file ${CERT_FILEPATH}

# Run Application
ENTRYPOINT ["java","-jar","-Dcom.ibm.mq.cfg.useIBMCipherMappings=false" ,"ibm-mq-client.jar"]