#!/bin/bash

if [ "$1" == "build" ]; then
  mvn clean package
fi

cp ./target/NexusBlock.jar ./tests/server/plugins/NexusBlock.jar
cd ./tests/server/
java -jar Rigot.jar