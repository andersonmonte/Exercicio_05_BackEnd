# Exercicio_05_BackEnd project

Projeto criado utilizando o framework Quarkus: https://quarkus.io/ .

## Extensões utilizadas no projeto

SmallRye OpenAPI (Swagger)
Hibernate ORM with Panache
JDBC Driver - MySQL
RESTEasy JSON-B
SmallRye Health

## Como rodar o projeto

Primeiramente, é necessaário criar um container docker com a base de dados MySQL:
```
docker run -p3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=teste -d --rm mysql:8.0.19
```
Alternativamente, este comando também pode ser utilizado, apenas com uma alteração na opção de porta:
```
docker run --network host -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=teste -d --rm mysql:8.0.19
```

Após a criação, você pode verificar que o container foi devidamente criado executando `docker ps`.

Com o banco de dados devidamente iniciado, basta entrar na pasta do projeto onde está o seguinte arquivo .jar e executá-lo:
```
Exercicio_05_BackEnd\target
```
```
java - jar Exercicio_05_BackEnd-dev.jar
```
A aplicação deverá estar disponível em http://localhost:8080/, e a documentação da api disponível em http://localhost:8080/swagger-ui.
