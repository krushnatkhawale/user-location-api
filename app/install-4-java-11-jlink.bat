@echo off

echo JLinking ( Compressing JVM ) -> Compressing JDK will be stored in 'myjdk'
jlink --output myjdk --module-path $JAVA_HOME/jmods --add-modules java.base,java.logging
echo ***************************************************************************
echo Building docker image
docker build -t krushnatkhawale/java-11-jlink -f Dockerfile-4-java-11-jlink .
echo ***************************************************************************
echo Running dockerized application
docker run -p 8080:8080 krushnatkhawale/java-11-jlink
