#!/usr/bin/env bash

cd App
echo "Starting :App:bootRun..."
../gradlew -q :App:bootRun &

echo "Waiting for connection..."
COUNTER="0"
while true; do
    curl -s http://localhost:8080
    if [ "$?" != "7" ]; then
        echo "Connection made"
        cd ../Client
        echo "Executing :Client:test..."
        ../gradlew :Client:test
        break
    elif [ "$COUNTER" -ge "10" ]; then
        echo "Connection timeout"
        break
    fi
    sleep 1
    COUNTER=$((COUNTER + 1))
    echo "${COUNTER} second(s) have passed"
done

echo "Will wait 10 seconds before stopping daemons."
sleep 10
../gradlew --stop