
# 💬 Real-Time Chat Application – Full Stack Web Project

A **full-stack real-time chat application** built with **React, Spring Boot, and WebSocket technology**, with a strong emphasis on **DevOps practices and CI/CD automation**. The application is **containerized using Docker** and deployed through a **Jenkins CI/CD pipeline**, leveraging **Jenkins Shared Libraries** to keep pipelines clean, reusable, and scalable.

----------

## 🚀 Project Overview

This project implements core real-time chat capabilities such as **user authentication and messaging** using a **React-based frontend** and a **Java Spring Boot backend**. The entire stack is fully containerized and automated using Jenkins, showcasing end-to-end DevOps workflows.

----------

## 🛠 Tech Stack

### 🧩 Application

-   **Frontend:** React.js, Tailwind CSS, Nginx
    
-   **Backend:** Java, Spring Boot, Maven
    
-   **Database:** MySQL (initialized via `init.sql`)
    
-   **API Style:** REST
    
-   **Real-Time Communication:** WebSocket
    

### ⚙️ DevOps & CI/CD

-   **CI/CD:** Jenkins (Shared Libraries)
    
-   **Containerization:** Docker, Docker Compose
    
-   **Image Registry:** Docker Hub
    
-   **Security Scanning:** Trivy (filesystem & image scanning)
    
-   **Web Server:** Nginx
    
-   **Version Control:** GitHub
    

----------

## ✨ Features

-   Real-time chat and user authentication
    
-   Modular frontend and backend services
    
-   Dockerized services with clean, reproducible builds
    
-   Nginx-based frontend delivery
    
-   Automated CI/CD pipeline using Jenkins
    
-   Filesystem and image vulnerability scanning with Trivy
    
-   Reusable Jenkins Shared Library for pipeline logic
    

----------

## 📁 Project Structure

```
.
├── Jenkinsfile
├── Readme.md
├── backend
│   ├── Dockerfile
│   ├── init.sql
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src/
├── docker-compose.yaml
└── frontend
    ├── Dockerfile
    ├── nginx.conf
    ├── package-lock.json
    ├── package.json
    ├── public/
    ├── src/
    └── tailwind.config.js

```

----------

## 🔁 CI/CD Pipeline Flow (Jenkins)

1.  Checkout source code from GitHub
    
2.  Run **Trivy filesystem scan**
    
3.  Build backend and frontend Docker images
    
4.  Run **Trivy image vulnerability scan**
    
5.  Push images to Docker Hub
    
6.  Deploy using Docker Compose
    

> Pipeline logic is implemented using a **Jenkins Shared Library**, ensuring consistency and reusability across stages.

----------

## 📦 Jenkins Shared Library

This project uses a custom **Jenkins Shared Library** to standardize CI/CD stages such as build, scan, push, and deploy.

🔗 **Jenkins Shared Library Repository:**  
[https://github.com/Harshavardhanchary/shared-library](https://github.com/Harshavardhanchary/shared-library)

### 📂 Example Library Structure

```
shared-library/
├── README.md
└── vars/
    ├── buildAndScanImages.groovy
    ├── chatPipeline.groovy
    ├── checkoutManifests.groovy
    ├── dockerLoginAndPush.groovy
    ├── gitLogin.groovy
    ├── prepareEnv.groovy
    ├── scanFiles.groovy
    └── updateDeployment.groovy

```

----------

## 🐳 Docker & Docker Compose

Run the complete application locally using Docker Compose:

```bash
docker-compose up -d --build

```
### 🧱 Services

-   Spring Boot backend container
    
-   React + Nginx frontend container
    
-   MySQL database container
    

----------

## 🔐 Security

-   Trivy filesystem scan before Docker builds
    
-   Trivy image scan after Docker image creation
    
-   Vulnerability results are published in Jenkins logs (fail/pass configurable)
    

----------

## 📌 Key Takeaways

-   Designing reusable Jenkins pipelines using Shared Libraries
    
-   Integrating security scanning into CI/CD pipelines
    
-   Dockerizing a Java Spring Boot and React-based application
    
-   Managing multi-container applications with Docker Compose
    

----------

## 📄 License

This project is intended for learning and portfolio demonstration purposes.

----------
