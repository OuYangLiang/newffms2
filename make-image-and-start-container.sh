#!/bin/bash

if [ "$#" -ne 1 ];then
    echo "Usage: make-image-and-start-container.sh ip-address"
    exit 1
fi

mvn -Dmaven.test.skip=true clean package

docker image build -t newffms:1.0 --build-arg IP_ADDRS=$1 .

docker container run -d -p 8080:8080 -p 9000:9000 newffms:1.0