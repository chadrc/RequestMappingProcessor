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

cd ../Client
echo "Executing :Client:run..."
../gradlew -q :Client:run

