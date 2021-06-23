# Spring Boot API Rest: Segurança da API, Cache e Monitoramento

- Para realizar paginação com Spring Data JPA, devemos utilizar a interface `Pageable`;
- Nas classes `Repository`, os métodos que recebem um `pageable` como parâmetro retornam objetos do tipo `Page<>`, ao invés de `List<>`;
- Para o Spring incluir informações sobre a paginação no JSON de resposta enviado ao cliente da API, devemos alterar o retorno do método do *controller* de `List<>` para `Page<>`;
- Para fazer a ordenação na consulta ao banco de dados, devemos utilizar também a interface `Pageable`, passando como parâmetro a direção da ordenação, utilizando a classe `Direction`, e o nome do atributo para ordenar;
- Para receber os parâmetros de ordenação e paginação diretamente nos métodos do *controller*, devemos habilitar o módulo `SpringDataWebSupport`, adicionando a anotação `@EnableSpringDataWebSupport` na classe `ForumApplication`.

---

- Para utilizar o módulo de cache do *Spring Boot*, devemos adicioná-lo como dependência do projeto no arquivo **pom.xml**;
- Para habilitar o uso de caches na aplicação, devemos adicionar a anotação `@EnableCaching` na classe `ForumApplication`;
- Para que o Spring guarde o retorno de um método no cache, devemos anotá-lo com `@Cacheable`;
- Para o Spring invalidar algum cache após um determinado método ser chamado, devemos anotá-lo com `@CacheEvict`;
- Devemos utilizar cache apenas para as informações que nunca ou raramente são atualizadas no banco de dados.

---

- Para utilizar o módulo do Spring Security, devemos adicioná-lo como dependência do projeto no arquivo **pom.xml**;
- Para habilitar e configurar o controle de autenticação e autorização do projeto, devemos criar uma classe e anotá-la com `@Configuration` e `@EnableWebSecurity`;
- Para liberar acesso a algum *endpoint* da nossa API, devemos chamar o método `http.authorizeRequests().antMatchers().permitAll()` dentro do método `configure(HttpSecurity http)`, que está na classe `SecurityConfigurations`;
- O método `anyRequest().authenticated()` indica ao Spring Security para bloquear todos os *endpoints* que não foram liberados anteriormente com o método `permitAll()`;
- Para implementar o controle de autenticação na API, devemos implementar a interface `UserDetails` na classe `Usuario` e também implementar a interface `GrantedAuthority` na classe `Perfil`;
- Para o Spring Security gerar automaticamente um formulário de login, devemos chamar o método `and().formLogin()`, dentro do método `configure(HttpSecurity http)`, que está na classe `SecurityConfigurations`;
- A lógica de autenticação, que consulta o usuário no banco de dados, deve implementar a interface `UserDetailsService`;
- Devemos indicar ao Spring Security qual o algoritmo de `hashing` de senha que utilizaremos na API, chamando o método `passwordEncoder()`, dentro do método `configure(AuthenticationManagerBuilder auth)`, que está na classe `SecurityConfigurations`.

---

- Em uma API Rest, não é uma boa prática utilizar autenticação com o uso de *session*;
- Uma das maneiras de fazer autenticação *stateless* é utilizando *tokens* **JWT** (*Json Web Token*);
- Para utilizar JWT na API, devemos adicionar a dependência da biblioteca **jjwt** no arquivo **pom.xml** do projeto;
- Para configurar a autenticação *stateless* no Spring Security, devemos utilizar o método `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)`;
- Para disparar manualmente o processo de autenticação no Spring Security, devemos utilizar a classe `AuthenticationManager`;
- Para poder injetar o `AuthenticationManager` no *controller*, devemos criar um método anotado com `@Bean`, na classe `SecurityConfigurations`, que retorna uma chamada ao método `super.authenticationManager()`;
- Para criar o *token* JWT, devemos utilizar a classe `Jwts`;
- O *token* tem um período de expiração, que pode ser definida no arquivo **application.properties**;
- Para injetar uma propriedade do arquivo **application.properties**, devemos utilizar a anotação `@Value`.

---

- Para enviar o *token* JWT na requisição, é necessário adicionar o cabeçalho `Authorization`, passando como valor `Bearer token`;
- Para criar um filtro no Spring, devemos criar uma classe que herda da classe `OncePerRequestFilter`;
- Para recuperar o *token* JWT da requisição no *filter*, devemos chamar o método `request.getHeader("Authorization")`;
- Para habilitar o filtro no Spring Security, devemos chamar o método `and().addFilterBefore(new AutenticacaoViaTokenFilter(), UsernamePasswordAuthenticationFilter.class)`;
- Para indicar ao Spring Security que o cliente está autenticado, devemos utilizar a classe `SecurityContextHolder`, chamando o método `SecurityContextHolder.getContext().setAuthentication(authentication)`.

---

- Para adicionar o **Spring Boot Actuator** no projeto, devemos adicioná-lo como uma dependência no arquivo **pom.xml**;
- Para acessar as informações disponibilizadas pelo **Actuator**, devemos entrar no endereço [http://localhost:8080/actuator](http://localhost:8080/actuator);
- Para liberar acesso ao **Actuator** no **Spring Security**, devemos chamar o método `.antMatchers(HttpMethod.GET, "/actuator/**")`;
- Para que o **Actuator** exponha mais informações sobre a API, devemos adicionar as propriedades `management.endpoint.health.show-details=always` e `management.endpoints.web.exposure.include=*` no arquivo **application.properties**;
- Para utilizar o **Spring Boot Admin**, devemos criar um projeto **Spring Boot** e adicionar nele os módulos `spring-boot-starter-web` e `spring-boot-admin-server`;
- Para trocar a porta na qual o servidor do **Spring Boot Admin** rodará, devemos adicionar a propriedade `server.port=8081` no arquivo **application.properties**;
- Para o **Spring Boot Admin** conseguir monitorar a nossa API, devemos adicionar no projeto da API o módulo `spring-boot-admin-client` e também adicionar a propriedade `spring.boot.admin.client.url=http://localhost:8081` no arquivo **application.properties**;
- Para acessar a interface gráfica do **Spring Boot Admin**, devemos entrar no endereço [http://localhost:8081](http://localhost:8081/).

---

- Para documentar a nossa API Rest, podemos utilizar o Swagger, com o módulo **SpringFox Swagger**;
- Para utilizar o **SpringFox Swagger** na API, devemos adicionar suas dependências no arquivo **pom.xml**;
- Para habilitar o Swagger na API, devemos adicionar a anotação `@EnableSwagger2` na classe `ForumApplication`;
- As configurações do Swagger devem ser feitas criando-se uma classe chamada `SwaggerConfigurations` e adicionando nela a anotação `@Configuration`;
- Para configurar quais *endpoints* e pacotes da API o Swagger deve gerar a documentação, devemos criar um método anotado com `@Bean`, que devolve um objeto do tipo `Docket`;
- Para acessar a documentação da API, devemos entrar no endereço [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html);
- Para liberar acesso ao Swagger no Spring Security, devemos chamar o seguinte método `web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**")`, dentro do método `void configure(WebSecurity web)`, que está na classe `SecurityConfigurations`.

---

## Atividade proposta

Já temos muitos alunos e alunas cadastradas e agora temos muito acesso para visualizar os perfis. Além disso, temos um novo endpoint de listagem e essa lista só cresce. Para fechar, é mais do que importante a gente controlar o acesso a tais informações.

Dado que as informações cadastradas dos alunos e alunas quase nunca muda, o que você faria para evitar que a recuperação dessa informação fosse feita sempre a partir do banco de dados?

Na listagem é importante trabalharmos com dados paginados. Descreva em detalhes os passos de implementação que você faria para possibilitar que a aplicação cliente pudesse acessar as informações de paginada e porque realizar a paginação é importante.

Para fechar, descreva como funciona o mecanismo de autenticação e autorização para uma API Rest através de tokens.

1. Para as informações cadastrais dos alunos, usaria cache na aplicação. Para isso, primeiro habilitaria o cache no Spring com a anotação `@EnableCaching` com isso, dizemos ao spring que usaremos cache. Em seguida, anotaria o endpoint com as informações de alunos com `@Cacheable` passando um Id para o mesmo. Dessa forma, digo ao spring que aquele endpoint é cacheável.
2. Para a listagem de alunos, ao invés de retornar um Response Entity de Lista de Alunos DTO, eu retornaria um Response Entity de Page de Alunos DTO , dessa forma, os dados que serão enviados para o cliente conterá os dados solicitados e as informações da paginação em JSON. Agora, adicionaria a interface Pageable no método para receber as informações da paginação. Com isso, passamos o `pageable` para o método findAll do nosso repository e o Spring já vai retornar uma lista paginável. Com a paginação em mãos, converteremos a página para lista e em seguida para os dtos. Retornamos o resultado.
3. A paginação é importante pra evitar que muitos dados sejam carregados de uma só vez. Dessa forma, não sobrecarregando a aplicação com milhares de dados.
4. Para o cliente acessar os dados paginado, só chamar o mesmo endpoint. Por padrão, ele já vem setado um número default de dados por página. Mas, caso o próprio cliente queira alterar, basta passar como parâmetro da URL, por exemplo: `/alunos?size=5`
5. Para ter o controle sobre quem acessa a lista, é configuramos o usuário da aplicação adicionando o Spring security. Lá existe uma interface chamada UserDetails que terá uma coleção de "privilégios" de cada usuário. Agora basta configurar no nosso spring security quem pode acessar cada endpoint de acordo com seu privilégio.
6. O mecanismo de autenticação funciona da seguinte forma: Como a comunicação em uma API é Stateless, ou seja, não guarda estado, precisamos identificar de alguma forma que o usuário está logado. Como fazer isso? Aí que entra o mecanismo de autenticação por Tokens. Ao realizar o login, a api devolve um token único para o cliente e esse token que vai dizer para a api se ele está logado ou não. Após isso, em todas as requisições futuras que necessitem estar logados, o cliente terá que enviar um Header chamado de Authorization contendo o tipo do token concatenado do próprio token. Daí a api irá verificar se o token é válido, se for, permite, senão, nega o acesso.