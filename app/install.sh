../gradlew.bat build
docker build -t krushnatkhawale/techpoint-springboot .
docker run -p 8080:8080 krushnatkhawale/techpoint-springboot
