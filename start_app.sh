#!/bin/sh

echo ""
echo ""
echo ""

sudo nohup java -jar app/build/libs/user-location-api.jar -Dspring.profiles.active=prod &

echo "        User location service launched successfully"
echo ""
echo ""
echo ""
