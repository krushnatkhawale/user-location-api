@echo off
echo Building docker image
docker build -t krushnatkhawale/java-11 -f Dockerfile-2-java-11 .
echo Running dockerized application
docker run -p 8080:8080 krushnatkhawale/java-11
