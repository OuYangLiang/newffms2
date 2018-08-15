#!/bin/bash

mvn -Dmaven.test.skip=true clean package

docker image build -t newffms:1.0 .

docker container run -d -p 8080:8080 -p 9000:9000 newffms:1.0