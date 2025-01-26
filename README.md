# Inventory Service

The **Inventory Service** is responsible for managing and updating the inventory of the system.

## Features

- Update inventory levels.
- Retrieve current inventory status.
- Log warnings for low inventory levels (via ConfigMap).

## Technologies Used

- **Java Spring Boot**
- **REST API**
- **In-memory Database**: Utilizes a **ConcurrentHashMap** for runtime inventory management. The ConcurrentHashMap is preloaded with some sample inventory data for demonstration purposes.
- **Docker**
- **Kubernetes** (with ConfigMap support)

## Main Endpoints

- `GET /inventory/check/{productId}`: Retrieves the stock of a specific product ID.
- `PUT /inventory/reduce/{productId}`: Reduces the stock for a given product ID and quantity.

## Setup and Deployment

1. **Docker**:
   - Build the image:
     ```bash
     docker build -t inventory-service .
     ```
   - Run the container:
     ```bash
     docker run -p 8082:8082 inventory-service
     ```

2. **Kubernetes**:
   - Apply deployment, service, and ConfigMap manifests:
     ```bash
     kubectl apply -f deployment.yaml
     kubectl apply -f service.yaml
     kubectl apply -f configmap.yaml
     ```

## Swagger Documentation

Swagger documentation is available at:

http://localhost:8082/swagger-ui.html