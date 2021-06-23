# Spring Boot e Teste Profiles, Testes e Deploy

- Para atualizar a versão do Spring Boot na aplicação, basta alterar a tag `<version>` da tag `<parent>`, no arquivo `pom.xml`.
- É importante ler as **release notes** das novas versões do Spring Boot, para identificar possíveis quebras de compatibilidades ao atualizar a aplicação.
- É possível restringir o acesso a determinados endpoints da aplicação, de acordo com o perfil do usuário autenticado, utilizando o método `hasRole(“NOME_DO_ROLE”)` nas configurações de segurança da aplicação.

---

- Profiles devem ser utilizados para separar as configurações de cada tipo de ambiente, tais como desenvolvimento, testes e produção.
- A anotação `@Profile` serve para indicar ao Spring que determinada classe deve ser carregada apenas quando determinados profiles estiverem ativos.
- É possível alterar o profile ativo da aplicação por meio do parâmetro `Dspring.profiles.active`.
- Ao não definir um profile para a aplicação, o Spring considera que o profile ativo dela e o profile de nome **default**.

---

Por que devemos anotar a classe de teste com `@SpringBootTest`?

Para que o Spring carregue os beans da aplicação durante os testes.

A anotação `@SpringBootTest` serve para que o Spring inicialize o servidor e carregue os beans da aplicação durante a execução dos testes automatizados.

- É possível escrever testes automatizados de classes que são beans do Spring, como `Controllers` e `Repositories`.
- É possível utilizar injeção de dependências nas classes de testes automatizados.
- A anotação `@SpringBootTest` deve ser utilizada nas classes de testes automatizados para que o Spring inicialize o servidor e disponibilize os beans da aplicação.
- Ao testar uma interface `Repository` devemos, preferencialmente, utilizar a anotação `@DataJpaTest`.
- Por padrão, os testes automatizados dos repositories utilizam um banco de dados em memória, como o h2.
- É possível utilizar outro banco de dados para os testes automatizados, utilizando a anotação `@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)`.
- É possível forçar um profile específico para os testes automatizados com a utilização da anotação `@ActiveProfiles`.
- Para conseguir injetar o `MockMvc` devemos anotar a classe de teste com `@AutoConfigureMockMvc`.

---

- O build da aplicação é realizado via maven, com o comando `mvn clean package`.
- Ao realizar o build, por padrão será criado um arquivo `.jar`.
- É possível passar parâmetros para as configurações da aplicação via variáveis de ambiente.
- É possível alterar o build para criar um arquivo `.war`, para deploy em servidores de aplicações.

---

- É possível utilizar o Docker para criação de imagens e de containers para aplicações que utilizam Java com Spring Boot.
- Devemos criar um arquivo `Dockerfile` no diretório raiz da aplicação, para ensinar ao Docker como deve ser gerada a imagem dela.
- É possível passar as variáveis de ambiente utilizadas pela aplicação para o container Docker.
- É possível realizar o deploy de aplicações Java com Spring Boot em ambientes Cloud, como o Heroku.
- Cada provedor Cloud pode exigir diferentes configurações adicionais a serem realizadas na aplicação, para que ela seja executada sem nenhum tipo de problema.

---

## Atividade proposta

Todas as respostas dadas pelos alunos e alunas do programa Orange Talent podem ser acessadas por mentores e programa managers que acompanham as turmas. Mas, como esse endereço reside no mesmo sistema onde os próprios alunos e alunas respondem às avaliações, é necessário controlarmos o acesso. Tanto aluno, como mentor, como program manager fazem login no mesmo sistema mas, por conta do nível de acesso, tem acessos a endereços diferentes.

Além disso, agora, para cada resposta dada por um aluno ou aluna, o sistema deve enviar um email para os(as) program managers avisando do evento. Só que tem um detalhe, em ambiente de desenvolvimento esse envio deve ser apenas de brincadeira, um mero print exibindo o email que deveria ser enviado. Só que em produção, o email deveria ser enviado usando o provedor de envio de emails que a Zup utiliza.

Descreva quais seriam os passos para você, usando o Spring Security, proteger os acessos em função do nível de acesso(roles) de cada tipo de usuário logado no sistema.

Descreva quais seriam os passos para você implementar o suporte a envios de emails de maneiras diferentes em função do ambiente de execução.

Roles e profiles...

1. Configurar o Spring Security na aplicação para que seja possível utilizar o controle de acesso.
2. Classe usuário implementar Interface UserDetails. Com essa classe, podemos adicionar os tipos de acessos para os usuários.
3. Criar entidade Perfil que vai servir para armazenar o tipo do acesso do usuário.
4. Adicionar uma lista de Perfil na entidade usuário, que será os acessos que o mesmo possui.
5. retornar a lista de perfil no método `getAuthorities()` para que sempre que o Spring precisar recuperar o acesso do usuário, recuperar por meio desse método.
6. Criar a classe que ficará responsável pelas configurações de segurança, estender de `WebSecurityConfigurerAdapter` que nos dará acesso aos métodos de configurações e, por fim, anotar com a classe com `@EnableWebSecurity`
7. Na classe, sobrescreveremos o método configure que tem o parâmetro com `HttpSecurity` , agora é só mapear as urls e métodos http para as Roles necessárias. Por exemplo: `.antMatchers(HttpMethod.POST, "/avaliacoes/*").hasRole("ALUNO")` que vai indicar para o Spring que nesse endpoint apenas os usuários com permissões de aluno podem realizar um POST.
8. Para o envio de emails, é necessário configurar os profiles. Criar outros 2 `application.properties`, um de desenvolvimento e um de produção.
9. Para o ambiente de desenvolvimento, usaríamos o profile de desenvolvimento. Teríamos uma classe anotada com @Profile("dev") para que o Spring execute essa classe quando estiver com o profile de desenvolvimento ativo. Agora, basta configurar o envio de emails na classe para que seja apenas de brincadeira.
10.  Já para o envio de emails de verdade, teríamos uma segunda classe anotada com @Profile("prod") com todas as configurações de envio de email necessárias. Em produção, setaríamos o profile ativo para `prod` e o Spring executará essa classe ao invés da outra que era só de brincadeira.
11. Importante ressaltar que no `[application-prod.properties](http://application-prod.properties)` as credencias sejam passadas como variáveis para que informações sensíveis não fique na nossa aplicação. Tais como: email, senha, database url, database password e por aí vai.