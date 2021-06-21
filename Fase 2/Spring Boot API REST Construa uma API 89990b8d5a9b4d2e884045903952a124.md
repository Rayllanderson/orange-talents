# Spring Boot API REST: Construa uma API

## Criando um projeto Spring boot

Para criar um projeto String, utilizamos o **Spring Initialzr** → [https://start.spring.io/](https://start.spring.io/)

1. Selecione as dependências
2. baixe o zip
3. unzip no projeto
4. e pronto, basta abrir na IDE
5. para inicializar o projeto com Spring Boot, devemos utilizar a classe com o método `main`;

Como vimos no vídeo anterior, foi necessário adicionar a anotação `@ResponseBody` em cima do método `lista`, na classe *controller*. Qual o objetivo dessa anotação?

Indicar ao Spring que o retorno do método deve ser devolvido como resposta. Por padrão, o Spring considera que o retorno do método é o nome da página que ele deve carregar, mas ao utilizar a anotação `@ResponseBody`, indicamos que o retorno do método deve ser *serializado* e devolvido no corpo da resposta.

- Para utilizar o JPA no projeto, devemos incluir o módulo `Spring Boot Data JPA`, que utiliza o **Hibernate**, por padrão, como sua implementação;
- Para configurar o banco de dados da aplicação, devemos adicionar as propriedades do *datasource* e do JPA no arquivo **`src/main/resources/application.properties`**;
- Para acessar a página de gerenciamento do banco de dados H2, devemos configurar o *console* do H2 com propriedades no arquivo **`src/main/resources/application.properties`**;
- Para mapear as classes de domínio da aplicação como entidade JPA, devemos utilizar as anotações `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`, `@OneToMany` e `@Enumerated`;
- Para que o Spring Boot popule automaticamente o banco de dados da aplicação, devemos criar o arquivo **`src/main/resources/data.sql`**;
- Para criar um *Repository*, devemos criar uma interface, que herda da interface `JPARepository` do Spring Data JPA;
- Para criar consultas que filtram por atributos da entidade, devemos seguir o padrão de nomenclatura de métodos do Spring, como por exemplo `findByCursoNome`;
- Para criar manualmente a consulta com JPQL, devemos utilizar a anotação `@Query`;

---

- Que para evitar repetir a URL em todos os métodos, devemos utilizar a anotação `@RequestMapping` em cima da classe *controller*;
- Que para mapear requisições do tipo POST, devemos utilizar a anotação `@PostMapping`;
- Que para receber dados enviados no corpo da requisição, a boa prática é criar uma classe que também siga o padrão **DTO** (*Data Transfer Object*);
- Que a boa prática para métodos que cadastram informações é devolver o código HTTP 201, ao invés do código 200;
- Que para montar uma resposta a ser devolvida ao cliente da API, devemos utilizar a classe `ResponseEntity` do Spring;
- Que para testar requisições do tipo POST, precisamos utilizar alguma ferramenta de testes de API Rest;
- Como utilizar o Postman para testar uma API Rest;

---

- Para fazer validações das informações enviadas pelos clientes da API, podemos utilizar a especificação ***Bean Validation***, com as anotações `@NotNull`, `@NotEmpty`, `@Size`, dentre outras;
- Para o Spring disparar as validações do *Bean Validation* e devolver um erro 400, caso alguma informação enviada pelo cliente esteja inválida, devemos utilizar a anotação `@Valid`;
- Para interceptar as *exceptions* que forem lançadas nos métodos das classes *controller*, devemos criar uma classe anotada com `@RestControllerAdvice`;
- Para tratar os erros de validação do *Bean Validation* e personalizar o JSON, que será devolvido ao cliente da API, com as mensagens de erro, devemos criar um método na classe `@RestControllerAdvice` e anotá-lo com `@ExceptionHandler` e `@ResponseStatus`.

---

- Para receber parâmetros dinâmicos no *path* da URL, devemos utilizar a anotação `@PathVariable`;
- Para mapear requisições do tipo `PUT`, devemos utilizar a anotação `@PutMapping`;
- Para fazer o controle transacional automático, devemos utilizar a anotação `@Transactional` nos métodos do *controller*;
- Para mapear requisições do tipo `DELETE`, devemos utilizar a anotação `@DeleteMapping`;
- Para tratar o erro 404 na classe *controller*, devemos utilizar o método `findById`, ao invés do método `getOne`, e utilizar a classe `ResponseEntity` para montar a resposta de *not found*;
- O método `getOne` lança uma *exception* quando o `id` passado como parâmetro não existir no banco de dados;
- O método `findById` retorna um objeto `Optional<>`, que pode ou não conter um objeto.

## Atividade proposta

Antes de pularmos para a parte da atividade onde você vai precisar descrever como faria determinada implementação, vamos começar pelo básico. Qual a utilidade do Spring Boot?

Agora, precisamos começar a construir um sistema para suportar nosso modelo de avaliações e respostas do Orange Talents. Para esse primeiro momento precisamos cadastrar novos(as) alunos(as) e também conseguir ver os detalhes de cada pessoa cadastrada.

Para a parte do cadastro, todo(a) aluno(a) precisa de um nome, email e idade. O nome não pode ter mais de 30 caracteres, o email também só pode ter no máximo 30 caracteres e a idade precisa ser maior ou igual a 18 anos. É importante que esses dados sejam cadastrados no banco de dados.

Usando o que foi visto durante o curso,  descreva todos os passos que você faria para conseguir receber os dados, validar, fazer com que as informações sejam persistidas no banco de dados e retornar um status 200 para a aplicação cliente em caso de sucesso ou 400 em caso de falha de validação.

Agora que o cadastro foi feito, é necessário que os detalhes de cada aluno(a) possam ser acessados. Uma restrição importante aqui, a identificação do(a) aluno(a) será feita pelo id do banco de dados e deve fazer parte do endereço de acesso. Para o detalhe, só precisamos exibir o nome e o email.

Usando o que foi visto durante o curso,  descreva todos os passos que você faria desde conseguir tratar a requisição feita para determinado endereço até retornar as informações do(a) aluno(a) em formato JSON.

### Resposta:

Em poucas palavras, o Spring Boot é um dos projetos do ecossistema Spring que se propõe a reduzir o tempo geral de desenvolvimento e aumentar a nossa eficiência. 

Então, a utilidade do Spring é que ele já vem com boa parte das configurações pré configuradas e prontas para uso, deixando que a gente foque inteiramente (ou quase) na regra de negócio, evitando todo aquelas configurações com XML, além de facilitar o deploy com seu Tomcat embutido. Não só isso, mas ele também é flexível! Como eu disse, ele vem com as configurações prontas, mas nada impede de você ir lá e modificar e de maneira bem simples.

1. Configurar o projeto Spring boot com as dependências Web, Spring data JPA, Bean Validation para que possamos começar nosso projeto focados na regra de negócio. A dependência Web irá habilitar o tomcat e o projeto MVC do Spring. a Data JPA será importante para persistência e a de Validation será útil para validar os campos antes de persistir.
2. Configurar o banco de dados da aplicação para que possamos nos conectar com o banco.
3. Criar e mapear com as anotações do JPA a entidade `Aluno`.
4. Criar interface `AlunoRepository` que entende da `JpaRepository` que irá ficar responsável pelos métodos CRUD já prontos.
5. Criar classe `AlunoDto` com os campos nome e email para lidar com as respostas da API.
6. Criar classe `AlunoForm` para receber os dados da requisição.
7. Validar os campos de aluno Form com as anotações do Bean Validation, tais como: `@NotNull @NotEmpty @Length(max = 30)` e para a idade: `@Min(18)`
8. Criar classe `AlunoController` mapeada com `@RequestMapping("/alunos")` para lidar com requisições no endpoint "/alunos". E, também, `@RestController` para indicar que as respostas dos nossos métodos serão no formato JSON.
9. Inserir AlunoRepository e a anotação `@Autowired` na mesma dentro da classe AlunoController para que possamos usar os métodos do repository.
10. Para salvar, criaria um método no nosso controller retornando um `ResponseEntity<AlunoDto>` para que possamos retornar um status adequado de maneira simples. No método, vamos receber um body do cliente, para isso, usamos na declaração dos parâmetros a anotação `@RequestBody` seguida do nosso Objeto `AlunoForm`. Também utilizaremos o `@Valid` do bean validation para validar o form antes mesmo de chegar no nosso método. Após validado, realizaremos a transformação de DTO para Entidade e então persistimos. Também criaremos uma URI com base no ID da nova entidade salva e retornaremos com `ResponseEntity.created(uri).body(alunoSalvoDto)`.
11. Caso falhe, criaremos uma classe anotada com `@RestControllerAdvice` que ficará encarregada de tratar as exceptions que acontecem nas requisições dos nossos controllers. Aqui criaremos um método anotada com `@ResponseStatus(code = HttpStatus.BAD_REQUEST)` para retornar o status 400 e `@ExceptionHandler(MethodArgumentNotValidException.class)` para dizer ao Spring que trataremos essa exception no método que estamos criando. Agora é só recuperar, setar os erros no nosso Pojo de validação e retornar.
12. Para retornar os detalhes, criar um método que retorna um objeto ResponseEntity de uma lista de `AlunoDto` anotada com a anotação `@GetMapping("/{id}")` indicando que toda requisição em /alunos/id-passado, irá cair nesse método.
13. Realizar uma busca desse ID pelo método do repository `findById` que irá nos retornar um optional. Lançar exceção e status 404 caso não encontre.
14. Fazer a transformação de Aluno para AlunoDto e retornar dentro do Objeto ResponseEntity com o método estático `ok()` que irá retornar o AlunoDto com resposta de status 200 OK.