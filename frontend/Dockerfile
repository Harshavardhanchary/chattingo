# Stage 1: Node.js build environment
FROM node:20-alpine AS build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json first to leverage Docker cache
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the React application
RUN npm run build

# Stage 2: Build React application (optional, already done in Stage 1)
# We can skip a separate stage since Stage 1 already creates the build

# Stage 3: Nginx runtime server
FROM nginx:alpine

# Copy React build files to Nginx html folder
COPY --from=build /app/build /usr/share/nginx/html

# Copy custom Nginx config for React routing (optional)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]

