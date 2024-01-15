#!/bin/bash

RED='\033[0;31m'
NC='\033[0m'

if [ -f "$1" ]; then

    filename=$(basename "$1")
    extension="${filename##*.}"
    if [  "$extension" != "pyc" ]; then
      echo -e "${RED}Please input a .pyc file\nExample: You can use command python -m compileall demo.py to get a .pyc file of demo.py${NC}"
      exit 1
    fi
else
    echo -e "${RED}Please input a .pyc file\nExample: You can use command python -m compileall demo.py to get a .pyc file of demo.py${NC}"
    exit 1
fi

java -jar target/jpvm-1.0-SNAPSHOT-jar-with-dependencies.jar $1
