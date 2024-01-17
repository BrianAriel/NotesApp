#!/bin/bash

# Function to check if a command is available
command_exists() {
  command -v "$1" >/dev/null 2>&1
}

# Check for Node.js and npm
if ! command_exists "node" || ! command_exists "npm"; then
  echo "Error: Node.js and npm are required. Please install them before running this script."
  exit 1
fi

# Check for Java
if ! command_exists "java"; then
  echo "Error: Java is required. Please install them before running this script."
  exit 1
fi

FRONTEND_DIR="../frontend/app"
BACKEND_DIR="../backend/app"

# Function to install frontend dependencies and build React app
setup_frontend() {
  echo "Setting up frontend..."
  cd "$FRONTEND_DIR"
  npm install
  npm run build
  npm start
  cd ..
  echo "Frontend setup complete."
}

# Function to set up backend and database
setup_backend() {
  echo "Setting up backend and database..."
  cd "$BACKEND_DIR"
  
  ./mvnw clean install
  ./mvnw spring-boot:run
  
  cd ..
  echo "Backend and database setup complete."
}

# Main function
main() {
  echo "Setting up and running the app..."

  # Run the frontend setup
  setup_frontend &

  # Run the backend setup
  setup_backend

  echo "App is now set up and running."

  # Automatic opening in the default browser
  xdg-open "http://localhost:3000"

  echo "Press Ctrl+C to stop the app."
}

# Run the main function
main
