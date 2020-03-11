# IBM-MQ-Client

This project demonstrates how to create a sample IBM MQ producer, based on JMS.

## Installation
_Example commands assume a *nix environment_

### Quick Start
_Docker must be installed and running (https://www.docker.com)_

1. Clone or download this repository.
2. Place the client SSL certificate in the root directory of this project.
3. Open a terminal and navigate to the root directory of this project.
4. Run the following command, replacing the `<cert-relative-filepath>` argument with the client SSL certificate filename: `docker build -t ibm-mq-client . && docker run --build-arg <cert-relative-filepath> ibm-mq-client`.

### Detailed Instructions
_Requires Oracle/OpenJDK JDK 11 & Maven 3_
1. Install the client SSL certificate the default Java `cacerts` keystore, via `keytool`
    - Run the following command, replacing `<cert-absolute-filepath>` with the filepath of the clientSSL certificate: `keytool -cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias omsmq -file <cert-absolute-filepath>`
2. Clone or download this repository.
3. Open a terminal and navigate to the root directory of this project.
4. Build & package: `mvn clean package`
5. Run the application: `java -jar -Dcom.ibm.mq.cfg.useIBMCipherMappings=false ibm-mq-client.jar`
