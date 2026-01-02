# Order Microservice (order-ms)

Microserviço de gerenciamento de pedidos desenvolvido com **Spring Boot 3.3**, **MongoDB** e **RabbitMQ**.

## 📋 Descrição

Este microserviço é responsável por:
- Consumir eventos de criação de pedidos via **RabbitMQ**
- Armazenar pedidos no banco de dados **MongoDB**
- Disponibilizar uma API REST para consulta de pedidos por cliente

## 🛠️ Tecnologias

- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Data MongoDB**
- **Spring AMQP (RabbitMQ)**
- **MongoDB**
- **RabbitMQ**
- **Maven**

## 📁 Estrutura do Projeto

```
src/main/java/matalvesdev/order_ms/
├── OrderMsApplication.java          # Classe principal da aplicação
├── config/
│   └── RabbitMqConfig.java          # Configuração do RabbitMQ
├── controller/
│   ├── OrderMsController.java       # Controller REST
│   └── dto/
│       ├── ApiResponse.java         # DTO de resposta da API
│       ├── OrderResponse.java       # DTO de resposta de pedido
│       └── PaginationResponse.java  # DTO de paginação
├── entity/
│   ├── OrderMsEntity.java           # Entidade de pedido
│   └── OrderItems.java              # Entidade de itens do pedido
├── listener/
│   ├── OrderCreatedListener.java    # Listener de eventos RabbitMQ
│   └── dto/
│       ├── OrderCreatedEvent.java   # DTO do evento de criação
│       └── OrderItemEvent.java      # DTO de item do evento
├── repository/
│   └── OrderMsRepository.java       # Repositório MongoDB
└── service/
    └── OrderMsService.java          # Serviço de negócio
```

## 🚀 Como Executar

### Pré-requisitos

- Java 21
- Maven
- Docker e Docker Compose

### 1. Iniciar a infraestrutura

Suba os containers do MongoDB e RabbitMQ:

```bash
cd infra
docker-compose up -d
```

Isso irá iniciar:
- **MongoDB** na porta `27017`
- **RabbitMQ** nas portas `5672` (AMQP) e `15672` (Management UI)

### 2. Executar a aplicação

```bash
./mvnw spring-boot:run
```

Ou via Maven:

```bash
mvn spring-boot:run
```

A aplicação será iniciada na porta `8080` (padrão do Spring Boot).

## 📡 API Endpoints

### Listar Pedidos por Cliente

```
GET /customers/{customerId}/orders
```

**Parâmetros de Query:**
| Parâmetro | Tipo    | Padrão | Descrição                     |
|-----------|---------|--------|-------------------------------|
| page      | Integer | 0      | Número da página              |
| pageSize  | Integer | 10     | Quantidade de itens por página|

**Exemplo de Request:**
```bash
curl -X GET "http://localhost:8080/customers/1/orders?page=0&pageSize=10"
```

**Exemplo de Response:**
```json
{
  "summary": {
    "totalOnOrders": 150.00
  },
  "data": [
    {
      "orderId": 1,
      "customerId": 1,
      "total": 75.00
    },
    {
      "orderId": 2,
      "customerId": 1,
      "total": 75.00
    }
  ],
  "pagination": {
    "page": 0,
    "pageSize": 10,
    "totalElements": 2,
    "totalPages": 1
  }
}
```

## 📨 Eventos RabbitMQ

### Queue: `order-created-ms`

O microserviço consome mensagens da fila `order-created-ms` com o seguinte formato:

```json
{
  "codigoPedido": 1,
  "codigoCliente": 123,
  "itens": [
    {
      "produto": "Produto A",
      "quantidade": 2,
      "preco": 25.00
    },
    {
      "produto": "Produto B",
      "quantidade": 1,
      "preco": 50.00
    }
  ]
}
```

## ⚙️ Configuração

O arquivo `application.properties` contém as configurações da aplicação:

```properties
spring.application.name=order-ms

# MongoDB
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.auto-index-creation=true
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=ordermsdb
spring.data.mongodb.username=admin
spring.data.mongodb.password=123

# RabbitMQ (configurações padrão do Spring AMQP)
# spring.rabbitmq.host=localhost
# spring.rabbitmq.port=5672
# spring.rabbitmq.username=guest
# spring.rabbitmq.password=guest
```

> **Nota:** O RabbitMQ utiliza as configurações padrão do Spring AMQP (localhost:5672 com guest/guest).

## 🐳 Docker Compose

O arquivo `infra/docker-compose.yml` configura os seguintes serviços:

| Serviço   | Imagem                     | Portas                  |
|-----------|----------------------------|-------------------------|
| MongoDB   | mongo                      | 27017:27017             |
| RabbitMQ  | rabbitmq:3.13-management   | 15672:15672, 5672:5672  |

### Credenciais Padrão

**MongoDB:**
- Usuário: `admin`
- Senha: `123`

**RabbitMQ:**
- Usuário: `guest`
- Senha: `guest`
- Management UI: http://localhost:15672

## 🧪 Testes

Para executar os testes:

```bash
./mvnw test
```

## 📦 Build

Para gerar o artefato JAR:

```bash
./mvnw clean package
```

O JAR será gerado em `target/order-ms-0.0.1-SNAPSHOT.jar`.

## 📄 Licença

Este projeto está sob a licença MIT.