#!/bin/bash

# Define text colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
RESET='\033[0m'  # Reset color

# Function to display the menu and prompt for user choice
show_menu() {
  echo -e "${BLUE}BUILD JAR FILE${RESET}"
  echo -e "${YELLOW}Choose a service to build:${RESET}"
  echo -e "${GREEN}1.${RESET} AuthorService"
  echo -e "${GREEN}2.${RESET} PostService"
  echo -e "${GREEN}3.${RESET} Both"
}

# Function to track and display elapsed time every second
track_time() {
  start_time=$(date +%s)  # Capture start time in seconds
  while :; do
    current_time=$(date +%s)
    elapsed_time=$((current_time - start_time))
    echo -ne "${YELLOW}Elapsed time: $elapsed_time seconds${RESET}\r"  # Display elapsed time
    sleep 1  # Update every second
  done
}

# Call the show_menu function to display options
show_menu

# Read user input for the choice
read -p "Please enter the number of your choice: " choice

# Define the log file path
LOG_FILE="build_log.txt"

# Handle the user's choice for building the JAR file
case $choice in
  1)
    echo -e "${GREEN}Building AuthorService...${RESET}"

    # Navigate to the AuthorService directory
    cd AuthorService || { echo -e "${RED}Error: Directory AuthorService not found!${RESET}"; exit 1; }

    # Create a log file and redirect Maven output to it
    touch "$LOG_FILE"

    # Start tracking time in the background
    track_time & 
    timer_pid=$!  # Save the PID of the timer process

    # Perform Maven build silently
    mvn clean install -DskipTests > "$LOG_FILE" 2>&1

    # Stop the timer
    kill $timer_pid

    echo -e "${GREEN}AuthorService build completed.${RESET}"

    # Delete the log file
    rm -f "$LOG_FILE"
    ;;

  2)
    echo -e "${GREEN}Building PostService...${RESET}"

    # Navigate to the PostService directory
    cd PostService || { echo -e "${RED}Error: Directory PostService not found!${RESET}"; exit 1; }

    # Create a log file and redirect Maven output to it
    touch "$LOG_FILE"

    # Start tracking time in the background
    track_time & 
    timer_pid=$!  # Save the PID of the timer process

    # Perform Maven build silently
    mvn clean install -DskipTests > "$LOG_FILE" 2>&1

    # Stop the timer
    kill $timer_pid

    echo -e "${GREEN}PostService build completed.${RESET}"

    # Delete the log file
    rm -f "$LOG_FILE"
    ;;

  3)
    echo -e "${GREEN}Building Both AuthorService and PostService...${RESET}"

    # Build AuthorService
    cd AuthorService || { echo -e "${RED}Error: Directory AuthorService not found!${RESET}"; exit 1; }
    touch "$LOG_FILE"
    track_time & 
    timer_pid=$!  # Save the PID of the timer process
    mvn clean install -DskipTests > "$LOG_FILE" 2>&1
    kill $timer_pid  # Stop the timer
    echo -e "${GREEN}AuthorService build completed.${RESET}"

    # Build PostService
    cd ../PostService || { echo -e "${RED}Error: Directory PostService not found!${RESET}"; exit 1; }
    touch "$LOG_FILE"
    track_time & 
    timer_pid=$!  # Save the PID of the timer process
    mvn clean install -DskipTests > "$LOG_FILE" 2>&1
    kill $timer_pid  # Stop the timer
    echo -e "${GREEN}PostService build completed.${RESET}"

    # Delete the log file after both builds
    rm -f "$LOG_FILE"
    ;;

  *)
    echo -e "${RED}Invalid choice. Please choose 1, 2, or 3.${RESET}"
    ;;
esac

# After build, ask about Docker deployment
echo -e "${BLUE}Process completed.${RESET}"
echo -e "${YELLOW}DEPLOYMENT DOCKER${RESET}"
echo -e "${YELLOW}Choose an action:${RESET}"
echo -e "${GREEN}1.${RESET} Build Docker Image"
echo -e "${GREEN}2.${RESET} Deploy Docker Containers"
echo -e "${GREEN}3.${RESET} Build and Deploy Both"

# Read user's choice for Docker deployment
read -p "Please enter the number of your choice: " docker_choice

# Handle Docker operations
case $docker_choice in
  1)
    echo -e "${GREEN}Building Docker images...${RESET}"

    # Run Docker Compose build
    docker-compose build

    echo -e "${GREEN}Docker images built successfully.${RESET}"
    ;;

  2)
    echo -e "${GREEN}Deploying Docker containers...${RESET}"

    # Run Docker Compose up (in detached mode)
    docker-compose up -d

    echo -e "${GREEN}Docker containers deployed successfully.${RESET}"
    ;;

  3)
    echo -e "${GREEN}Building Docker images and deploying containers...${RESET}"

    # Run Docker Compose build
    docker-compose build
    echo -e "${GREEN}Docker images built successfully.${RESET}"

    # Run Docker Compose up (in detached mode)
    docker-compose up -d
    echo -e "${GREEN}Docker containers deployed successfully.${RESET}"
    ;;

  *)
    echo -e "${RED}Invalid choice. Please choose 1, 2, or 3.${RESET}"
    ;;
esac
