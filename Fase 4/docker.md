## Criando uma imagem de uma aplicação Spring boot

### Passo 1: Criar o Dockerfile
 1. Crio o docker file
```dockerfile
FROM openjdk:11.0.11-jdk-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]
```
2. Executo o mvn pra criar o target do projeto: `mvn clean package`. Caso queira pular os testes durante a criação do target:
  `mvn package -Dmaven.test.skip=true` 
3. Crio o docker-compose.yml
  ```yml
  nome:
    build:
      dockerfile: Dockerfile
      context: .
    image: seu-nome/nome-da-imagem
    container_name: nome-do-container
    ports:
      - 8081:8081 #caso queira especificar a porta
    environment:
      SERVER_PORT: 8081 #coloquei aqui pra garantir
      DATABASE_URL: jdbc:postgresql://postgres:5432/postgres
      DATABASE_USERNAME: postgres
      DATABASE_PASSWORD: 12345
      API_SOLICITATION_URL: http://analise:9999/api/solicitacao #simulando comunicação com outro sistema aqui do docker
    networks:
      - propostas-network #network, bem importante. Os serviços que se comunicam aqui dentro, precisam estar na mesma network
    depends_on:
      - postgres #vai subir só depois que esse aqui levantar
```

4. Bonus: Criando o banco de dados Postgres:
```yml
    postgres:
    image: 'postgres:alpine'
    ports:
      - 5432:5432
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 12345
    networks:
      - propostas-network 
 ```
