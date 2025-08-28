# Documentação da API de Tarefas

## Visão Geral

API RESTful para gerenciamento de tarefas simples, construída com Spring Boot, usando banco H2 em memória.

***

## Endpoints

### Criar uma nova tarefa

- **URL:** `/task`
- **Método:** `POST`
- **Headers:** `Content-Type: application/json`
- **Corpo (JSON):**

```json
{
  "nome": "Tarefa Exemplo",
  "descricao": "Descrição da tarefa",
  "status": "true"
}
```
- **Resposta:** Retorna o objeto criado com status HTTP 201 CREATED.

***

### Listar todas as tarefas

- **URL:** `/task`
- **Método:** `GET`
- **Resposta:** Lista JSON com todas as tarefas cadastradas.

***

### Buscar tarefa por ID

- **URL:** `/task/{id}`
- **Método:** `GET`
- **Parâmetros:**  
  `id` - UUID da tarefa
- **Resposta:** Objeto JSON da tarefa correspondente ou 404 se não encontrada.

***

### Atualizar uma tarefa

- **URL:** `/task/{id}`
- **Método:** `PUT`
- **Headers:** `Content-Type: application/json`
- **Parâmetros:**  
  `id` - UUID da tarefa a ser atualizada
- **Corpo (JSON):**

```json
{
  "nome": "Tarefa Atualizada",
  "descricao": "Descrição atualizada",
  "status": "false"
}
```
- **Resposta:** Objeto atualizado com status 200 OK.

***

### Deletar uma tarefa

- **URL:** `/task/{id}`
- **Método:** `DELETE`
- **Parâmetros:**  
  `id` - UUID da tarefa a ser removida
- **Resposta:** Status 204 No Content em caso de sucesso.

***

## Exemplos de Curl

- Criar:

```bash
curl -i -X POST -H 'Content-Type: application/json' -d '{"nome":"Tarefa1","descricao":"Descricao da tarefa","status":"true"}' http://localhost:8081/task
```

- Listar:

```bash
curl -i http://localhost:8081/task
```

- Buscar por ID:

```bash
curl -i http://localhost:8081/task/{id}
```

- Atualizar:

```bash
curl -i -X PUT -H 'Content-Type: application/json' -d '{"nome":"Tarefa Atualizada","descricao":"Descricao atualizada","status":"false"}' http://localhost:8081/task/{id}
```

- Deletar:

```bash
curl -i -X DELETE http://localhost:8081/task/{id}
```

***

## Observações

- O campo `id` é gerado automaticamente e é do tipo UUID.
- Campos obrigatórios: `nome`, `descricao`, `status`.
- O campo `status` representa se a tarefa está ativa (true/false).
- Banco de dados em memória H2 usado para desenvolvimento e testes.
- Para configuração do application.properties:
```
spring.application.name=RegistroDeTarefas
server.port=8080 

### database H2 configurations
spring.datasource.url=jdbc:h2:mem:"escolha o nome do banco"
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username="nome do adm do banco"
spring.datasource.password="senha para o adm, porém caso queria pode deixar em branco"
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update
### observações: para acessar o banco use a url "http://localhost:{nome da port que escolheu}/h2-console/"

```


