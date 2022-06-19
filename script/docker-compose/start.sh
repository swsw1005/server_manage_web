apt update && apt install net-tools iproute2 -y
java -jar  /usr/local/server-manager/server-manager.war --spring.config.additional-location=file:///usr/local/server-manager/application.properties