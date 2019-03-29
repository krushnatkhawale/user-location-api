@echo off
echo Building docker image
docker build -t krushnatkhawale/java-11-alpine -f Dockerfile-3-java-11-alpine .
echo Running dockerized application
docker run -p 8080:8080 krushnatkhawale/java-11-alpine
