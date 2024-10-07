# Inventory Management

## 💡 About

Inventory Management is a system designed for e-commerce platforms,
enabling internal employees to manage products, update inventories, and retrieve product details.
The focus is on implementing a RESTful API that provides CRUD operations for products.

## ⚙️ Setup

### Prerequisites

* **Docker**: Ensure Docker is installed on your machine.

### Configuration

1. **Ports Availability**:
    * Ensure that ports `8080` (for the API) and `5432` (for PostgreSQL) are available on your machine.

2. **Environment Variables**:
    * The necessary environment variables are predefined in the `.env` file located in the project root. Ensure this file exists and contains the correct
      configurations.

Execute the following command in your terminal to build and run the application:

```shell
docker-compose up -d
```

This command will:

* Build the Docker image for the API.
* Start the PostgreSQL database.
* Launch the API service.

## 🚀 Usage

Once the services are up and running, you can interact with the API using tools like curl or Postman.

### API Endpoints

**Get All Products**

* Endpoint: GET /products
* Description: Retrieves a list of all products.

```shell
curl http://localhost:8080/api/products
```

**Get Product by ID**

* Endpoint: GET /products/{id}
* Description: Retrieves details of a specific product by its ID.

```shell
curl http://localhost:8080/api/products/1
```

**Add New Product**

* Endpoint: POST /products
* Description: Adds a new product to the inventory.

```shell
curl -X POST http://localhost:8080/api/products \
-H "Content-Type: application/json" \
-d '{
  "name": "Sample Product",
  "description": "A great product",
  "price": 49.99,
  "stock": 100
}'
```

**Update Existing Product**

* Endpoint: PUT /products/{id}
* Description: Updates details of an existing product.

```shell
curl -X PUT http://localhost:8080/api/products/1 \
-H "Content-Type: application/json" \
-d '{
  "name": "Updated Product",
  "description": "Updated description",
  "price": 59.99,
  "stock": 80
}'
```

**Delete Product**

* Endpoint: DELETE /products/{id}
* Description: Removes a product from the inventory.

```shell
curl -X DELETE http://localhost:8080/api/products/1
```