# Cadastro de Pessoas - Java Jakarta EE

Este projeto é uma aplicação educacional simples para cadastro de pessoas, desenvolvida utilizando Java Jakarta EE, Payara Micro e Maven. O objetivo principal é ensinar conceitos de Gerência de Configuração e Manutenção de Software.

## Pré-requisitos
- Java 20
- Docker
- PostgreSQL

## Estrutura do Projeto
A aplicação permite cadastrar pessoas informando um nome e uma cidade. Não há tratamento para duplicação de IDs e o ID `0` não pode ser utilizado.

## Observação Importante
O diretório do projeto **não pode conter espaços** no nome, pois isso pode gerar erros na execução.

---
## Configuração Local
Caso precise alterar a porta do servidor Payara, edite o arquivo `pom.xml`, alterando o valor dentro da tag `<value>`:

```xml
<commandLineOptions>
    <option>
        <key>--port</key>
        <value>8080</value>
    </option>
</commandLineOptions>
```

---
## Executando a Aplicação

### Execução Local
1. Configure o Banco de dados no arquivo `src\main\java\com\cp\data\crud\interfaces\EMNames.java`
2. Compile o projeto:
   ```sh
   mvn clean package
   ```
3. Execute o Payara Micro com a aplicação:
   ```sh
   java -jar payara-micro.jar --deploy target/cadpessoas.war --port 8080
   ```
4. Acesse no navegador:
   ```sh
   http://localhost:8080/Cadastro-Pessoas/
   ```

### Execução com Docker

#### Método 1 - Sem Rede Docker
```sh
mvnw.cmd clean package

docker run -it --rm --name myPostgresDb -p 5432:5432 \
    -e POSTGRES_USER=<database_user> -e POSTGRES_PASSWORD=<database_password> -e POSTGRES_DB=localdb \
    -d postgres

docker build -t cadpessoas:v1 .

docker run -it --rm \
    -e DATABASE_URL="jdbc:postgresql://myPostgresDb:5432/localdb" \
    -e DATABASE_USERNAME=<database_user> -e DATABASE_PASSWORD=<database_password> \
    --link myPostgresDb:myPostgresDb -p 8080:8080 cadpessoas:v1
```
Acesse:
```sh
http://localhost:8080/Cadastro-Pessoas/
```

#### Método 2 - Com Rede Docker
```sh
mvnw.cmd clean package

docker network create myNetwork

docker run -it --rm --name myPostgresDb --network myNetwork -p 5432:5432 \
    -e POSTGRES_USER=<database_user> -e POSTGRES_PASSWORD=<database_password> -e POSTGRES_DB=localdb \
    -d postgres

docker build -t cadpessoas:v1 .

docker run -it --rm --network myNetwork \
    -e DATABASE_URL="jdbc:postgresql://myPostgresDb:5432/localdb" \
    -e DATABASE_USERNAME=<database_user> -e DATABASE_PASSWORD=<database_password> \
    -p 8080:8080 cadpessoas:v1
```
Acesse:
```sh
http://localhost:8080/Cadastro-Pessoas/
```

---
## CI/CD com GitHub Actions
A aplicação possui um pipeline de CI/CD que, ao realizar um push para a branch `main`, gera automaticamente uma nova imagem Docker e a publica no Docker Hub.

### Arquivo `.github/workflows/docker-publish.yml`:
```yaml
name: Publish Docker Image

on:
  push:
    branches:
      - main

jobs:
  push_to_docker_hub:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Log in to Docker Hub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKERHUB_USERNAME }}
          password: ${{ secrets.DOCKERHUB_TOKEN }}

      - name: Build Docker image
        run: docker build -t <seu_usuario>/cadpessoas:latest .

      - name: Push Docker image
        run: docker push <seu_usuario>/cadpessoas:latest
```

### Configuração de Segredos no GitHub
Antes de utilizar o pipeline, configure os seguintes segredos no repositório:
- `DOCKERHUB_USERNAME`: Seu nome de usuário no Docker Hub.
- `DOCKERHUB_TOKEN`: Token de acesso ao Docker Hub.
- `<seu_usuario>/cadpessoas`: É seu repositório no Docker Hub.

---
## Análise Estática de Código com PMD
Este projeto utiliza o PMD para análise estática do código-fonte.

### Para executar o PMD no projeto
```sh
mvnw.cmd pmd:pmd
mvnw.cmd pmd:check
```

### Salvar o resultado em um arquivo TXT
```sh
mvnw.cmd pmd:check > resultado_pmd.txt
```

### Visualizar o relatório em HTML
Abra o arquivo gerado em:
```sh
target\reports\pmd.html
```

### Projeto em execução
![image](https://github.com/user-attachments/assets/bc5b32bb-d7d2-48cc-ac25-5e1d7018bd91)


---
