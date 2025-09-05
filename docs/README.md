# Documento de Arquitetura – Sistema Gerenciador de Tarefas

**Projeto:** Sistema Gerenciador de Tarefas
**Tipo:** API RESTful
**Tecnologias:** Java 17+, Spring Boot, Spring Data JPA, Spring Security (JWT), H2, Maven, JUnit, Swagger, Bootstrap
**IDE:** Eclipse

---

## 1. Decisões Técnicas

### 1.1 Tecnologias e Frameworks Escolhidos e Justificativas

* **Java 17+:** Linguagem consolidada, multiplataforma, com amplo suporte de bibliotecas e comunidade.
* **Spring Boot:** Simplifica a configuração e execução da aplicação, oferecendo injeção de dependências, MVC e integração nativa com JPA.
* **Spring Data JPA:** Abstrai o acesso ao banco de dados, reduzindo a necessidade de SQL explícito e aumentando a produtividade.
* **Spring Security com JWT:** Garante autenticação e autorização segura via tokens.
* **Banco H2:** Banco em memória (ou arquivo local) utilizado pela facilidade de configuração em desenvolvimento.
* **Maven:** Ferramenta de build e gestão de dependências.
* **JUnit 5:** Suporte a testes unitários e de integração.
* **Swagger/OpenAPI:** Documentação interativa da API.
* **Bootstrap:** Utilizado para fornecer um front-end simples de consumo, responsivo e padronizado.

---

### 1.2 Estrutura de Pastas e Organização do Projeto

```
src/main/java/com/exemplo/tarefas/
├── controller/         → APIs REST (TaskController, AuthController)
├── service/            → Regras de negócio (TaskService, UserService)
├── repository/         → Interfaces JPA (TaskRepository, UserRepository)
├── model/              → Entidades (Task, User, Role)
├── security/           → Configurações JWT, filtros e providers
├── dto/                → Objetos de transferência (TaskDTO, UserDTO, LoginRequest)

src/main/resources/
├── application.properties → Configurações do Spring e do H2
├── data.sql               → Dados iniciais (opcional)

src/test/java/com/exemplo/tarefas/
→ Testes unitários e de integração

pom.xml → Dependências e build
```

---

### 1.3 Estratégias de Separação de Responsabilidades

* **Controller:** Exposição de endpoints REST (`/api/tasks`, `/api/auth`)
* **Service:** Encapsula regras de negócio (validações, status, relacionamentos)
* **Repository:** Abstração de persistência com Spring Data JPA
* **Model:** Define entidades persistentes mapeadas em tabelas H2
* **Security:** Autenticação (login, geração/validação de tokens JWT) e autorização (roles)
* **DTOs:** Evitam exposição direta de entidades, melhorando segurança e desacoplamento

---

### 1.4 Classes Essenciais e Regras de Negócio

#### Camada de Modelo (Entities)

* **Task:** Representa a tarefa (id, título, descrição, status, prazo, usuário responsável)
* **User:** Representa o usuário do sistema (id, username, email, senha)
* **Role:** Define papéis/permissões (ex.: ROLE\_USER, ROLE\_ADMIN)

#### Camada de Repositório

* **TaskRepository:** Interface que estende `JpaRepository<Task, Long>`
* **UserRepository:** Interface que estende `JpaRepository<User, Long>`, com métodos como `findByUsername`
* **RoleRepository (opcional):** Para buscar papéis de usuários

#### Camada de Serviço (Regras de Negócio)

* **TaskService:** Cria/atualiza/exclui tarefas, valida regras como evitar exclusão de tarefas de outros usuários
* **UserService:** Gerencia cadastro de usuários, busca por username/email e associa roles

#### Camada de Segurança

* **JwtUtil / JwtService:** Geração e validação de tokens JWT
* **JwtRequestFilter:** Filtro que intercepta requisições e valida o token
* **SecurityConfig:** Configuração do Spring Security, definindo permissões de endpoints
* **CustomUserDetailsService:** Carrega usuários e papéis do banco para autenticação

#### Camada de Controller (Endpoints REST)

* **TaskController:** CRUD de tarefas (`/api/tasks`)
* **AuthController:** Autenticação e registro (`/api/auth`)

---

## 2. Evolução e Escalabilidade

### 2.1 Propostas de Evolução da Aplicação

* **Banco de dados robusto:** Migração do H2 para PostgreSQL ou MySQL em produção
* **Autenticação avançada:** Uso de OAuth2 ou Keycloak para gestão de usuários
* **Versionamento de API:** Inclusão de prefixos `/api/v1`, `/api/v2` para manter compatibilidade
* **Cache:** Uso de Redis para melhorar tempo de resposta em consultas repetidas
* **Paginação e filtros:** Suporte a paginação em listagens e filtros dinâmicos por status, prioridade ou usuário
* **CI/CD:** Pipelines de integração e entrega contínua com GitHub Actions ou GitLab CI
* **Escalabilidade horizontal:** Deploy em containers Docker e orquestração com Kubernetes

### 2.2 Sugestões Técnicas para Performance e Manutenção

* Definição clara de DTOs para isolar o domínio interno da exposição pública
* Logs estruturados com SLF4J + Logback
* Índices em colunas frequentemente consultadas (status, deadline)
* Testes automatizados cobrindo serviços e endpoints REST
* Monitoramento com Actuator (Spring Boot) + Prometheus/Grafana

---

## 3. Simulação de Distribuição de Tarefas em Equipe

### 3.1 Organização entre Três Desenvolvedores

#### **Dev 1 – Backend (Core da API):**

* Criar entidades Task e User
* Implementar TaskService e TaskRepository
* Criar endpoints REST no TaskController

#### **Dev 2 – Segurança e Frontend (consumo da API):**

* Implementar autenticação JWT (AuthController, filtros, configs)
* Criar entidade Role e fluxo de login
* Desenvolver interface básica com Bootstrap para consumir a API

#### **Dev 3 – Testes e Documentação:**

* Criar testes unitários (JUnit + Mockito)
* Testes de integração de endpoints (Spring Test, MockMvc)
* Configurar Swagger/OpenAPI para documentação automática
* Escrever manual de uso (como rodar a API, exemplos de requisições)

### 3.2 Como Manter Qualidade de Código

* **Git workflow:** Uso de feature branches e pull requests
* **Code review:** Revisão cruzada antes do merge para garantir padrão e qualidade
* **Checkstyle / SpotBugs:** Ferramentas de análise estática de código
* **CI/CD:** Execução automática de testes a cada commit
* **Definition of Done:** Funcionalidade só é concluída quando possui testes, documentação e revisão

---

## 4. Exemplos de Fluxos de Uso

### 4.1 Fluxo de Autenticação

1. O usuário envia uma requisição `POST /api/auth/login` com username e password
2. O sistema valida as credenciais via `CustomUserDetailsService`
3. Se válidas, o `JwtUtil` gera um token JWT assinado
4. O token é retornado ao cliente e usado no cabeçalho `Authorization: Bearer <token>` em chamadas subsequentes

### 4.2 Fluxo de Registro de Usuário

1. Requisição `POST /api/auth/register` com dados do novo usuário
2. O `UserService` valida se o username já existe
3. A senha é criptografada (BCrypt) e o usuário salvo com `ROLE_USER`
4. O cliente pode então efetuar login normalmente

### 4.3 Fluxo de Criação de Tarefa

1. O cliente envia `POST /api/tasks` com título e descrição, incluindo o token JWT no cabeçalho
2. O `JwtRequestFilter` valida o token e identifica o usuário autenticado
3. O `TaskService` valida os dados (ex.: título não pode ser vazio)
4. A tarefa é persistida no banco via `TaskRepository`
5. O endpoint retorna os dados da tarefa criada com status `201 Created`

### 4.4 Fluxo de Listagem de Tarefas

1. O cliente envia `GET /api/tasks` com o token JWT no cabeçalho
2. O filtro valida o token e identifica o usuário
3. O `TaskService` busca apenas as tarefas pertencentes ao usuário autenticado
4. A resposta retorna a lista em JSON, podendo incluir paginação e filtros

---

## 5. Conclusão

A arquitetura proposta segue o padrão **RESTful** com **Spring Boot**, aplicando boas práticas de separação de responsabilidades, segurança com **JWT**, persistência via **H2** e documentação com **Swagger**.

O sistema é simples para desenvolvimento inicial, mas já preparado para evoluir para cenários mais complexos em produção.

