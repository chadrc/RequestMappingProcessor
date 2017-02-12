#!/usr/bin/env bash

cd App
echo "Starting :App:bootRun..."
../gradlew -q :App:bootRun &

echo "Waiting for connection..."
SECONDS="0"
while true; do
    curl -s http://localhost:8080
    if [ "$?" != "7" ]; then
        echo "Connection made"
        cd ../Client
        echo "Executing :Client:test..."
        ../gradlew :Client:test
        break
    elif [ "$SECONDS" -ge "10" ]; then
        echo "Connection timeout"
        break
    fi
    sleep 1
    SECONDS=$((SECONDS+1))
    echo "${SECONDS} second(s) have passed"
done

echo "Will wait 5 seconds before stopping daemons."
sleep 5
../gradlew --stop