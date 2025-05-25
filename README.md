# l2code

Projeto Spring Boot para empacotamento de produtos. AVALIAÇÃO TÉCNICA

---

## Descrição

Este projeto é uma aplicação backend construída com Spring Boot 3.4.3 e Java 17. Tem como objetivo principal o processamento e empacotamento de pedidos com produtos, utilizando validação, documentação OpenAPI e suporte a JSON Web Tokens (JWT).

---

## Tecnologias

- Java 17
- Spring Boot 3.4.3
- Spring Web
- Spring Validation
- Lombok (para reduzir boilerplate)
- JWT (biblioteca `jjwt` versão 0.11.5)
- Springdoc OpenAPI (para documentação automática da API)
- JUnit 5 e Mockito para testes

---

## Autenticação

Para acessar a aplicação, utilize as seguintes credenciais padrão:

Username: admin
Password: admin123

Essas credenciais permitem autenticar na API e acessar os endpoints protegidos por JWT. Certifique-se de incluir o token JWT gerado no header Authorization (formato Bearer <token>) para chamadas aos endpoints protegidos.

## Configuração do projeto

Este é um projeto Maven. Para rodar a aplicação localmente, certifique-se de ter:

- Java 17 instalado
- Maven instalado

### Build e execução

```bash
mvn clean install
mvn spring-boot:run


