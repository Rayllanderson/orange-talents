# Health Check, Readiness & Liveness Check e Spring Boot Actuator

Nossas aplicações normalmente rodam em um ambiente gerenciado, ou seja, uma plataforma que é capaz de detectar se algo não está funcionando conforme o esperado, um crash de aplicação ou carga demasiada. Depois de notar essas falhas, as plataformas são capazes de tomar algumas ações para tentar remediar a aplicação, reiniciar ou mesmo desligar a aplicação e lançá-la novamente em outro servidor.

## Readiness Check ou Liveness Check

- Readiness Check - Para verificar se uma aplicação está apta ou não para receber os tráfegos da aplicação. É um processo que pode ser a configuração de uma fila, conexão com um banco de dados ou um determinado pré-processamento. Durante esses testes, é importante que nenhuma requisição seja enviada para que não atrapalhe os testes.

caso a plataforma verifique que a aplicação ainda não está pronta para utilização, não enviará tráfego para ela. Uma vez que a plataforma executa a verificação e nota que o status retornado foi de sucesso, as requisições começam a ser redirecionadas para esta nova instância de serviço.

- Liveness Check - Verifica se ocorreu algum erro na aplicação;

Agora imagine que nossa aplicação está sendo executada normalmente, mas por algum motivo deixou de ser capaz de processar as requisições que chegam até ela (problemas de vazamento de memória, deadlock, etc) e está presa em um estado de falha. Utilizando o Liveness Check a plataforma identifica que a aplicação não é capaz de processar as requisições e reiniciará o container da aplicação na tentativa de restaurá-la e permitir a retomada do seu funcionamento normal.

Em uma Rest Api, geralmente essas verificações são feitas através de um GET. Os endpoints devem verificar se a aplicação está apta a receber fluxo de trabalho (Readiness check) e se as requisições estão sendo processadas de boas (Liveness check) 

# **Como configurar o Spring Boot Actuator?**

O Spring Boot Actuator inclui vários recursos adicionais para ajudá-lo a monitorar e gerenciar sua aplicação, como por exemplo:

- Endpoint para monitoramento da saúde da aplicação (Health Check).
- Endpoint para expor métricas da aplicação.
- Endpoint para expor as propriedades da sua aplicação.

Vamos configurar?

1º Precisamos adicionar a seguinte dependência em seu arquivo `pom.xml`:

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
</dependencies>
```

2º Precisamos verificar se está tudo configurado, para isso abra em seu navegador o seguinte endereço:

`http://localhost:8080/actuator`

Neste endpoint irá listar todos os endpoints configurados, como por exemplo:

`# Endpoint para monitoramento da saúde da aplicação (Health Check)

http://localhost:8080/actuator/health`

Está tudo OK!

---

Imaginando que as API's criadas até agora utilizando Spring Boot estão sendo executadas em um ambiente gerenciado e capaz de monitorar a saúde de API's, como podemos prepará-las para fornecerem as informações necessárias para que esse mecanismo de monitoramento funcione corretamente?

Como o ambiente solicita a informação da saúde da aplicação para a mesma?

1. Adiciona a dependência do actuator no nosso projeto via pom.xml.
2. Acessando [http://localhost:8080/actuator](http://localhost:8080/actuator) temos os endpoints liberados. Por padrão, o Health já vem habilitado. Então não precisamos fazer mais nada! Apenas acessar. Mas, caso não esteja, basta adicionar no nosso .properties: `management.endpoints.exposure.include=health` agora estamos expondo o endpoint de health.
3. Para funcionar corretamente, é importante controlar o acesso e adicionar um CORS na aplicação para evitar vazamento de dados, além de, claro, expor somente os endpoints necessários. 
4. Para solicitar as informações de saúde basta acessar → `[http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)` se o cliente tiver autorizado e com a URL do CORS configurada na nossa aplicação, ele vai conseguir acessar.