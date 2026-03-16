# 📋 ClienteCrediário API

API REST desenvolvida em Java com Spring Boot para gerenciamento de clientes e empréstimos com sistema de crédito baseado em tipo de relacionamento.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **Spring Validation**
- **PostgreSQL**
- **Lombok**
- **Swagger / OpenAPI 2.5.0**
- **Flyway**
- **Maven**

---

## ⚙️ Instalação e Configuração

### Pré-requisitos

- Java 17+
- Maven
- PostgreSQL

### 1. Clone o repositório
```bash
git clone https://github.com/GenesonFerreira/ClienteCrediario.git
cd ClienteCrediario
```

### 2. Configure o banco de dados

No arquivo `src/main/resources/application.properties`, configure suas credenciais:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/clientecrediario
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### 3. Execute o projeto
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

A documentação Swagger estará disponível em `http://localhost:8080/swagger-ui.html`

---

## 📐 Arquitetura do Projeto
```
src/main/java/com/cadcliente/clientecrediario/
├── controller/
│   ├── ClienteController.java
│   └── EmprestimoController.java
├── service/
│   ├── ClienteService.java
│   └── EmprestimoService.java
├── repository/
│   ├── ClienteRepository.java
│   └── EmprestimoRepository.java
├── model/
│   ├── Cliente.java
│   ├── Emprestimo.java
│   └── TipoRelacionamento.java
├── DTOs/
│   ├── ClienteRequestDTO.java
│   ├── ClienteResponseDTO.java
│   ├── EmprestimoRequestDTO.java
│   └── EmprestimoResponseDTO.java
└── exception/
    └── GlobalExceptionHandler.java
```

---

## 📌 Endpoints

### 👤 Clientes

| Método | URL | Descrição |
|--------|-----|-----------|
| `POST` | `/clientes` | Cadastrar cliente |
| `GET` | `/clientes` | Listar todos os clientes |
| `GET` | `/clientes/buscar/id/{id}` | Buscar cliente por ID |
| `GET` | `/clientes/buscar/cpf/{cpf}` | Buscar cliente por CPF |
| `PUT` | `/clientes/atualizar/id/{id}` | Atualizar cliente por ID |
| `PUT` | `/clientes/atualizar/cpf/{cpf}` | Atualizar cliente por CPF |
| `DELETE` | `/clientes/deletar/id/{id}` | Deletar cliente por ID |
| `DELETE` | `/clientes/deletar/cpf/{cpf}` | Deletar cliente por CPF |

#### Exemplo de requisição — Cadastrar cliente
```json
{
    "nome": "João Silva",
    "cpf": "12345678901",
    "rg": "1234567",
    "telefone": "83999999999",
    "email": "joao.silva@email.com",
    "rendimentoMensal": 3000.00
}
```

---

### 💰 Empréstimos

| Método | URL | Descrição |
|--------|-----|-----------|
| `POST` | `/clientes/{id}/emprestimos` | Adicionar empréstimo |
| `GET` | `/clientes/{id}/emprestimos` | Listar empréstimos por ID do cliente |
| `GET` | `/clientes/cpf/{cpf}/emprestimos` | Listar empréstimos por CPF do cliente |
| `PUT` | `/emprestimos/{id}` | Atualizar empréstimo |
| `DELETE` | `/emprestimos/{id}` | Deletar empréstimo |

#### Exemplo de requisição — Adicionar empréstimo
```json
{
    "valorInicial": 1000.00,
    "relacionamento": "BRONZE",
    "dataInicial": "01/04/2025",
    "dataFinal": "01/04/2026"
}
```

---

## 📊 Regras de Negócio

### 💳 Tipos de Relacionamento e Juros

| Tipo | Regra |
|------|-------|
| `BRONZE` | 80% de juros sobre o valor inicial |
| `PRATA` | 60% de juros até R$5.000 / 40% acima de R$5.000 |
| `OURO` | 20% no primeiro empréstimo / 30% nos seguintes |

#### Exemplos de cálculo
```
BRONZE → R$1.000 × 1,80 = R$1.800
PRATA  → R$2.000 × 1,60 = R$3.200
PRATA  → R$6.000 × 1,40 = R$8.400
OURO   → R$1.000 × 1,20 = R$1.200 (primeiro empréstimo)
OURO   → R$1.000 × 1,30 = R$1.300 (empréstimos seguintes)
```

### 🔒 Limite de Crédito

O valor total dos empréstimos de um cliente não pode ultrapassar **10x o seu rendimento mensal**.
```
Rendimento: R$3.000 → Limite total: R$30.000
```

### ✅ Validações

- Nome obrigatório (entre 3 e 100 caracteres)
- CPF obrigatório e único (11 dígitos numéricos)
- E-mail com formato válido
- Telefone com 10 ou 11 dígitos
- Valor do empréstimo maior que zero
- Datas no formato `dd/MM/yyyy`

---

## 🚨 Tratamento de Erros

| Status | Situação |
|--------|----------|
| `400 Bad Request` | Dados inválidos ou limite de crédito excedido |
| `404 Not Found` | Cliente ou empréstimo não encontrado |
| `409 Conflict` | CPF já cadastrado |
| `500 Internal Server Error` | Erro interno inesperado |

---

## 📝 Versionamento

| Versão | Descrição |
|--------|-----------|
| `v0.1.0` | CRUD de clientes |
| `v0.2.0` | DTOs, validações e tratamento de erros |
| `v0.3.0` | Módulo de empréstimos com regras de negócio |

---

## 👨‍💻 Autor

Desenvolvido por **Geneson Ferreira**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/genesonferreira/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/GenesonFerreira)