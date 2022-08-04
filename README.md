<h1 align="center">Bem-vindo à API de Gerenciamento de Tarefas 👋</h1>

<h2> Este serviço tem como finalidade permitir que usuários devidamente cadastrados possam gerenciar suas tarefas. 

## :pushpin: Info
Este projeto faz parte de um exercício que tem como objetivo testar os conhecimentos em desenvolvimento de APIs utilizando Spring.

Os requisitos do projeto está disponível no arquivo [requisitos.md](https://github.com/JuniorBMonteiro/task-api/blob/main/requisitos.md).<br>   
Foi realizado o deploy da aplicação no Heroku, e ele pode ser acessado no seguinte endereço:

[Task-API | Heroku](https://plan-tasks-api.herokuapp.com/)

```sh
https://plan-tasks-api.herokuapp.com/
```

<h6>Acesse a sessão de Endpoints para mais informações</h6>

  
## 📋 Pre-requisites
Certifique-se de ter instalado as seguintes ferramentas:

>* Java 11+
>* Maven
>* PostgreSQL ou Docker
<h6>Também é necessário ter um banco de dados no postgreSQL com o nome "task" antes da execução do projeto e um com o nome "task_teste" para execução dos testes!<h6>  

## :wrench: Build 
Para realizar o build deste projeto execute no diretório do projeto o comando abaixo:

```sh
mvn clean package -DskipTests
```

## 🚀 Run

Após ter realizado o build é necessario ir até a pasta /target onde estará o .jar gerado.  
Para executar a aplicação, dentro da pasta /target execute o comando abaixo, preenchendo com suas variáveis de ambiente:

```sh
 java -jar -Dspring.profiles.active=prod -DDATABASE_URL=jdbc:postgresql://localhost:5432/task -DDATABASE_USERNAME=? -DDATABASE_PASSWORD=? -DEXPIRATION=? -DSECRET=? task-0.0.1-SNAPSHOT.jar

```

## 🚀 Run with Docker
>Imagem docker do projeto: <a href="https://hub.docker.com/r/juniorbmonteiro/task-api">Task API - Docker Hub</a> 

Foi publicado uma imagem do projeto no Docker Hub, para executá-lo basta utilizar o seguinte comando, preenchendo com suas variáveis de ambiente:

```sh
docker run -p 8080:8080 -e -SPRING_PROFILES_ACTIVE=prod -e DATABASE_URL=jdbc:postgresql://localhost:5432/task -e DATABASE_USERNAME=? -e DATABASE_PASSWORD=? -e EXPIRATION=? -e SECRET=? --net=host juniorbmonteiro/task-api
```

Na raiz do projeto foi disponibilizado o arquivo docker-compose.yml.  

* docker-compose.yml
  * Task-api
  * PostgreSQL
  
Para executar a aplicação, execute o comando abaixo:
```sh
 docker-compose up
```

## :key: Environment variable

|Name|Description|
|---|---|
|SPRING_PROFILES_ACTIVE|Ambiente de execução|
|DATABASE_URL|Endereço do banco de dados|
|DATABASE_USERNAME|Usuário do banco de dados|
|DATABASE_PASSWORD|Senha do banco de dados|
|EXPIRATION|Tempo de duração do token|
|SECRET|Segredo utilizado na autenticação|

## :heavy_check_mark: Run tests

Testes Unitários
```sh
mvn clean test
```
Testes de Integração
```sh
mvn clean test -Pintegration-tests
```
Testes Unitários e de Integração
```sh
mvn clean test -Pall-tests
```
## :dart: Endpoints
Os endpoints disponíveis podem ser vistos na documentação do Swagger:
* [Site](https://plan-tasks-api.herokuapp.com/swagger-ui/index.html)
* [Local](http://localhost:8080/swagger-ui.html)

## 🛠️ Technologies

Ferramentas utilizadas no desenvolvimento deste projeto:
* [Java](https://www.oracle.com/br/java/) - Linguagem de Programação
* [Spring](https://spring.io/) - Framework de desenvolvimento Web
* [Maven](https://maven.apache.org/) - Gerenciador de Dependência
* [Docker](https://www.docker.com/) - Criação e gerenciamento de containers

## ✒️ Author

👤 **Junior Monteiro**

* Github: [@JuniorBMonteiro](https://github.com/JuniorBMonteiro)
* LinkedIn: [@linkedin.com\/in\/juniorbmonteiro](https://linkedin.com/in/linkedin.com\/in\/juniorbmonteiro)
* Email: [juniorbmonteiro@hotmail.com](juniorbmonteiro@hotmail.com)

## 📄 License

Este projeto está sob a licença Apache License 2.0 - veja o arquivo [LICENSE.md](https://github.com/JuniorBMonteiro/task-api/blob/main/LICENSE) para detalhes.

## 🎁 Contributing

Contribuições, problemas ou dúvidas são bem-vindos.
