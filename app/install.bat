@echo off
echo Building docker image
docker build -t krushnatkhawale/techpoint-springboot .
echo Running dockerized application
docker run -p 8080:8080 krushnatkhawale/techpoint-springboot
