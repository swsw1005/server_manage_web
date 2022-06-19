#! /bin/bash

# JAVA_HOME=/c/dev/runtime/openJDK/jdk-11

# mvn clean

rm -rf server-manager.war

mvn install  -Dmaven.test.skip=true

mv target/server-manager-1.0.0.war  ./server-manager.war