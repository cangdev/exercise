#!/bin/bash

APPLICATION_NAME=LIVE_BET_APP

# Path to the JAR file (located in the same directory as the script)
JAR_PATH="./liveBet-0.0.1-SNAPSHOT.jar"

# Log file path
LOG_FILE="./logs/application.log"

# Check if the application is already running
if pgrep -f "$JAR_PATH" > /dev/null
then
    echo "The application is already running."
    exit 1
else

        echo "....................STARTING $APPLICATION_NAME........................"
    mkdir -p ./logs # Create logs directory if it doesn't exist
    nohup /appdata/jdk/jdk-17.0.11/bin/java -jar -Dapplication=$APPLICATION_NAME "$JAR_PATH" > "$LOG_FILE" 2>&1 &
    echo "....................$APPLICATION_NAME STARTED........................."
fi