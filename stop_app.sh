#!/bin/sh

echo ""
echo ""
echo ""

for pid in `ps -ef | grep "sudo nohup java -jar app/build/libs/user-location-api.jar &" | awk '{print $2}'` ; do sudo kill $pid ; done

echo ""
echo ""
echo "                Application user-location-api stopped"
echo ""
echo ""
echo ""