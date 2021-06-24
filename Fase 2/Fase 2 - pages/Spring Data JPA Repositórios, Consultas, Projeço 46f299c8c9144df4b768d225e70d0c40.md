# Spring Data JPA: Repositórios, Consultas, Projeções e Specifications

- configurar nosso projeto Spring para conectar ao banco de dados;
- criar tabelas de forma automática e alterar o nome delas conforme a necessidade da aplicação;
- utilizar os repositórios do framework para trazer agilidade nas nossas operações de CRUD.

---

## Atividade proposta

Precisamos possibilitar a inserção, atualização, exibição e remoção de uma avaliação criada por mentores(as). Além disso, vamos querer buscar as avaliações pesquisando pelos títulos delas.

Descreva exatamente quais passos você faria para possibilitar que tais operações fossem feitas usando o Spring Data JPA aproveitando o máximo de coisas prontas da tecnologia.

Pergunta bônus: Se você só cria interface no Spring Data JPA, onde está a real implementação?

### Resposta

1. Instalar dependência do Spring Data JPA no nosso projeto Spring boot para que possamos começar a usar o módulo.
2. Criar e mapear a entidade Avaliação para que possamos ter mais controle sobre nossos dados e também será necessário para utilizar a nossa interface de repositório.
3. Criar um Repositório de avaliação que estende de `CrudRepository` para que o Spring nos disponibilize métodos comuns de CRUD. Passaremos a nossa entidade de Avaliação para que o Spring entenda que estamos trabalhando com a entidade Avaliação. Também passaremos o tipo do ID, no nosso caso, Long.
4. Para inserir, utilizaremos o método `save` do nosso repositório, esse método já vem pronto! Vamos apenas utilizar direto.
5. Para atualizar, precisaremos do ID da avaliação, pois se não houver nenhum ID, a avaliação será salva ao invés de atualizada. Com o id em mãos, basta mudar o que quer e usar o mesmo  método, o `save` do nosso repository.
6. Para listar, utilizaremos o método do repository chamado de `findAll()` ele já vai retornar todos os dados da tabela entidade. Agora basta percorrer e ir imprimindo.
7. Para deletar, vamos precisar do ID. Podemos também deletar a entidade toda, mas, aqui, vamos utilizar o `deleteById` do nosso repository. Passado o id, ele vai deletar pra gente.
8. Para procurar por título, usaremos um recurso poderoso do Spring Data JPA, a Derived Queries. Ela nos permite realizar consultas de forma simples. Somente precisamos usar uma convenção de nomes. Por exemplo, para procurar pelo título, usaríamos, `find` - significa que queremos procurar - `by` - indica que o que vier em seguida, ser a seleção de busca - `title` - no nosso caso, pelo título. Então ficaria: `findByTitle` e o retorno, uma List de avaliações, visto que nossa consulta retorna várias avaliações. Além disso, podemos usar o LIKE também: `findByTitleLike` que dessa forma vai trazer os registros sem nos preocuparmos com lower e upper caso, por exemplo. Vale ressaltar que o `title` é um atributo da nossa entidade `Avaliacao`, portanto, depois do `by` deve-se usar um atributo da nossa entidade, podendo ele também ser um objeto.

A real implementação das interfaces, são feitas em tempo de execução, o Spring cria cada uma delas baseadas nos 'parâmetros' que passamos ao criar a interface. Então, por exemplo, o nosso repository de Avaliações, passamos o tipo da nossa entidade e o tipo do id, essas são informações importantes que o Spring irá se basear nelas para criar uma implementação em tempo de execução. Aqui está um exemplo:  `public interface AvaliacaoRepository extends CrudRepository<Avaliacao, Long>`