@echo off
echo Building docker image
docker build -t krushnatkhawale/java -f Dockerfile-0-java .
echo Running dockerized application
docker run -p 8080:8080 krushnatkhawale/java
