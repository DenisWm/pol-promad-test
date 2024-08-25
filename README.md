# MICROSERVIÇO DE PROCESSOS

## Descrição
Esta é uma aplicação Spring Boot que utiliza Gradle para gerenciamento de dependências e PostgreSQL como banco de dados. A aplicação é containerizada usando Docker e Docker Compose. Utiliza Liquibase para versionamento de banco de dados e possui documentação com OpenAPI. É um microserviço com testes unitários, integrados, ponta a ponta, e suporte a serialização e desserialização.

## Estrutura do Projeto
- **Java**: Linguagem de programação utilizada.
- **Gradle**: Ferramenta de automação de build.
- **Spring Boot**: Framework para criação de aplicações Java.
- **PostgreSQL**: Banco de dados relacional.
- **Liquibase**: Ferramenta para versionamento de banco de dados.
- **OpenAPI**: Ferramenta para documentação de APIs.
- **Docker**: Plataforma para desenvolvimento, envio e execução de aplicações em containers.
- **Docker Compose**: Ferramenta para definir e gerenciar multi-containers Docker.

## Pré-requisitos
- JDK 21
- Docker
- Docker Compose

## Configuração

### Docker
1. Construa e inicie os containers Docker (incluindo a aplicação):
    ```sh
    docker-compose up --build
    ```

### Gradle + Docker
1. Suba apenas o banco de dados:
    ```sh
    docker-compose up db
    ```
   
2. Para construir o projeto:
    ```sh
    gradle build
    ```

3. Para executar a aplicação:
    ```sh
    gradle bootRun
    ```

## Estrutura dos Arquivos
- `domain/src/main/java/com/pol/promad/test/domain/`: Código relacionado à lógica de negócios.
- `application/src/main/java/com/pol/promad/test/application/`: Código relacionado à aplicação e serviços.
- `infrastructure/src/main/java/com/pol/promad/test/infrastructure/Main.java`: Classe principal que inicia a aplicação Spring Boot.
- `docker-compose.yml`: Arquivo de configuração do Docker Compose.
- `Dockerfile`: Arquivo de configuração do Docker para construir a imagem da aplicação.

## Endpoints
A aplicação estará disponível em `http://localhost:8080/api`.

## Banco de Dados
- **Usuário**: `postgres`
- **Senha**: `postgres`
- **Nome do Banco**: `adm_processos`
- **Porta**: `5432`

## Documentação
A documentação da API está disponível via OpenAPI em `http://localhost:8080/api/swagger-ui.html`.

## Princípios Utilizados

### DDD (Domain-Driven Design)
- **Entidades**: Objetos que possuem identidade própria.
- **Objetos de Valor**: Objetos que são definidos por seus atributos.
- **Agregados**: Conjunto de entidades e value objects que são tratados como uma unidade.
- **Gateways**: Interfaces para acesso a dados.
- **Casos de Uso**: Contêm lógica de negócios que não se encaixa em entidades ou value objects.

### TDD (Test-Driven Development)
- **Red-Green-Refactor**: Ciclo de desenvolvimento onde primeiro se escreve um teste que falha (Red), depois se escreve o código mínimo para passar o teste (Green) e por fim se refatora o código (Refactor).
- **Testes Unitários**: Testes que verificam o comportamento de unidades individuais de código.
- **Testes de Integração**: Testes que verificam a interação entre diferentes unidades de código.
- **Testes de Ponta a Ponta**: Testes que verificam o comportamento do sistema como um todo.
- **Testes de Serialização e Desserialização**: Testes que verificam o comportamento dos objetos de entrada e saída da API.

## Licença
Este projeto está licenciado sob os termos da licença Publicações Online e PROMAD.