#!/usr/bin/env bash

./gradlew -q clean

cd Processor
echo "Executing :Processor:publishToMavenLocal..."
../gradlew -q :Processor:publishToMavenLocal

cd ../App
echo "Executing :App:build..."
../gradlew -q :App:build

echo "Executing :App:publishToMavenLocal..."
../gradlew -q :App:publishToMavenLocal

echo "Starting :App:bootRun..."
../gradlew -q :App:bootRun &

echo "Waiting for connection..."
SECONDS="0"
while true; do
    curl -s http://localhost:8080
    if [ "$?" != "7" ]; then
        echo "Connection made"
        cd ../Client
        echo "Executing :Client:run..."
        ../gradlew -q :Client:run
        break
    elif [ "$SECONDS" -ge "10" ]; then
        echo "Connection timeout"
        break
    fi
    sleep 1
    SECONDS=$[$SECONDS]
    echo "${SECONDS} second(s) have passed"
done

echo "Will wait 5 seconds before stoping daemons."
sleep 5
../gradlew --stop

