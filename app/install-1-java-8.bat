@echo off
echo Building docker image
docker build -t krushnatkhawale/java-8 -f Dockerfile-1-java-8 .
echo Running dockerized application
docker run -p 8080:8080 krushnatkhawale/java-8
