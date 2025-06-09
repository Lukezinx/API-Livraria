# 🚀 API Livraria

API RESTful para gerenciamento completo de livros, autores e editoras, desenvolvida em Java 17+ com Spring Boot. O projeto é containerizado com Docker 🐳 e utiliza PostgreSQL 🐘 como banco de dados relacional. Além disso, possui busca paginada e ordenação, tratamento de erros centralizado, validações e testes automatizados via Postman 📬.

---

## ✨ Funcionalidades

- CRUD completo para as entidades:
  - 📚 Livros
  - ✍️ Autores
  - 🏢 Editoras
- 🔍 Busca paginada e ordenável para listagem de livros, autores e editoras.
- ✅ Validação dos dados de entrada usando Bean Validation (`@Valid`, `@NotBlank`, etc).
- 🚨 Tratamento global de exceções com respostas padronizadas.
- 🔐 Uso de DTOs para entrada e saída, evitando exposição direta das entidades do banco.
- ⚙️ Configuração via variáveis de ambiente para facilitar a parametrização em diferentes ambientes.
- 🧪 Testes automatizados com Postman para garantir a qualidade dos endpoints.

---

## 🛠️ Tecnologias Utilizadas

- ☕ Java 17+
- 🌱 Spring Boot (MVC, Spring Data JPA)
- 🐘 PostgreSQL
- 🐳 Docker & Docker Compose
- 📬 Postman (testes automatizados)
- 📦 Maven (gerenciamento de dependências)

---

## 🚀 Como Rodar o Projeto

1. **Clone o repositório**

```bash
git clone https://github.com/Lukezinx/API-Livraria.git
cd API-Livraria
```

2. **Configure as variáveis de ambiente**

Configure as credenciais do banco no arquivo `application.properties` ou defina as variáveis no `docker-compose.yml`.

3. **Execute via Docker Compose**

```bash
docker-compose up --build
```

Este comando sobe a API e o banco PostgreSQL em containers isolados.

## 📁 Estrutura do Projeto

- `controller/` – Classes responsáveis por receber as requisições HTTP.
- `service/` – Lógica de negócio da aplicação.
- `repository/` – Comunicação com o banco via JPA.
- `entity/` – Mapeamento das entidades do banco.
- `dto/` – Objetos usados para transferência de dados (entrada e saída).
- `exception/` – Tratamento global de erros e exceções.
- `config/` – Configurações do Spring e do banco.

---

## 🔗 Endpoints Principais

- `/api/livros` – CRUD e busca paginada de livros.
- `/api/autores` – CRUD e busca paginada de autores.
- `/api/editoras` – CRUD e busca paginada de editoras.

---

## 💡 Considerações Finais

Este projeto foi desenvolvido com foco em boas práticas, organização do código e escalabilidade. Ideal para estudos avançados em APIs RESTful e integração contínua com Docker.
