
# Documentação Técnica – Tecnologias Utilizadas

## Visão Geral

Este projeto é uma aplicação web desenvolvida com Spring Boot, utilizando arquitetura RESTful, banco de dados em memória para testes, autenticação via JWT e documentação automatizada com OpenAPI (Swagger).

---

## Tecnologias Utilizadas

### Spring Boot

* Versão: 3.5.5
* Framework principal do projeto.
* Simplifica a criação e configuração de aplicações Java modernas.
* Utiliza starters para incluir dependências comuns com convenções padrão.

### Spring Web (Spring MVC)

* Dependência: `spring-boot-starter-web`
* Usado para criar controladores REST e lidar com requisições HTTP.
* Fornece suporte para criação de APIs RESTful.
* Utiliza Tomcat embutido como servidor web padrão.

### Spring Data JPA

* Dependência: `spring-boot-starter-data-jpa`
* Integração com bancos de dados relacionais usando JPA (Java Persistence API).
* Hibernate é a implementação JPA padrão.
* Facilita persistência de dados com repositórios e anotações.

### H2 Database

* Dependência: `com.h2database:h2`
* Banco de dados relacional em memória, ideal para testes e desenvolvimento local.
* Não requer instalação externa.
* Permite acesso via console web (se habilitado).

### Spring Security

* Dependência: `spring-boot-starter-security`
* Framework de autenticação e autorização para proteger endpoints da aplicação.
* Permite integração com autenticação baseada em token (JWT), Basic Auth, OAuth2, entre outras.

### JWT (JSON Web Tokens)

* Dependências:

  * `jjwt-api`
  * `jjwt-impl`
  * `jjwt-jackson`
* Biblioteca para geração, verificação e parsing de tokens JWT.
* Utilizada para implementar autenticação stateless com tokens.
* Integra-se com Spring Security para proteger endpoints.

### Bean Validation

* Dependência: `spring-boot-starter-validation`
* Fornece validação automática de dados de entrada usando anotações como `@NotNull`, `@Size`, `@Email`, entre outras.
* Baseado no padrão Jakarta Bean Validation (Hibernate Validator).

### Testes

* Dependência: `spring-boot-starter-test`

  * Inclui JUnit, Mockito, Spring Test e outras bibliotecas comuns para testes.
* Dependência: `spring-security-test`

  * Permite simular usuários e testar segurança dos endpoints protegidos.

### Documentação da API (OpenAPI/Swagger)

* Dependência: `springdoc-openapi-starter-webmvc-ui`
* Gera automaticamente a documentação da API com base nos controladores e anotações do Spring.
* Disponibiliza interface interativa Swagger UI.
* Endpoints gerados:

  * `/swagger-ui.html` ou `/swagger-ui/index.html`: interface visual.
  * `/v3/api-docs`: especificação OpenAPI em JSON.

### Spring JDBC (opcional)

* Dependência: `spring-boot-starter-jdbc`
* Permite acesso direto ao banco de dados usando JDBC e `JdbcTemplate`.
* Útil em cenários onde o uso de JPA não é viável.

---

## Build e Configuração

### Maven

* Ferramenta de build utilizada no projeto.
* Arquivo principal: `pom.xml`
* Plugin para empacotamento:

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

### Java

* Versão utilizada: Java 17
* Definida em:

```xml
<java.version>17</java.version>
```

---

## Organização do Projeto (sugestão)

```
src/
└── main/
     ├── java/
     │    └── com.br.registrodetarefas
     │         ├── config
     │         ├── controllers
     │         ├── exceptions
     │         ├── models/
     │         ├── repositories/
     │         ├── services/
     │         └── utils/
     └── resources/
          ├── application.properties
          ├── application-test.properties
          ├── static/
          └── templates/
test/
└── static/     # Todos os arquivos de teste
```

---

## Execução de Testes e Build

### Comandos disponíveis

A aplicação pode ser compilada e testada diretamente via terminal usando o Maven wrapper (`./mvnw`). Os comandos abaixo devem ser executados **a partir da raiz do projeto**, ou seja, onde está localizado o arquivo `pom.xml`.

**Executar testes automatizados:**

```bash
./mvnw test
```

Esse comando roda todos os testes definidos no projeto, incluindo testes unitários e de integração.

**Gerar build completo do projeto:**

```bash
./mvnw clean install
```

Esse comando:

* Remove builds anteriores (`clean`)
* Compila o código
* Executa os testes
* Empacota o projeto em um JAR executável (`target/*.jar`)

> Observação: Certifique-se de estar dentro da pasta do projeto onde está localizado o `pom.xml` ao rodar esses comandos.

---

Esta stack é adequada para o desenvolvimento de APIs modernas, seguras e escaláveis. As tecnologias utilizadas favorecem a produtividade durante o desenvolvimento, além de oferecerem suporte nativo para testes, validação e documentação.


