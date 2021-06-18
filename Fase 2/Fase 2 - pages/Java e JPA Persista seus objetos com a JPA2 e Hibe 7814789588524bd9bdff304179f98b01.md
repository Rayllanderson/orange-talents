# Java e JPA: Persista seus objetos com a JPA2 e Hibernate

### [Código fonte](https://github.com/Rayllanderson/orange-talents/tree/main/Fase%202/treinamento-jpa)

#

```
 em.getTransaction().begin();

        em.persist(conta);

        em.getTransaction().commit();
        em.close();

        conta.setSaldo(500.0);
```

## Configurações Iniciais

Para começar um projeto com hibernate, siga os passos:

1. Criar um novo projeto Maven
2. Criar uma pasta chamada de `META-INF` dentro de `src/main/resources`
3. Criar um arquivo `persistence.xml` dentro da `META-INF`
4. Preencher os dados no `persistence.xml` 

    ```xml
    <persistence xmlns="http://java.sun.com/xml/ns/persistence"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://java.sun.com/xml/ns/persistence
            http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
                 version="2.0">

        <persistence-unit name="Preencher o nome aqui">
            <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

            <class>com.rayllanderson.jpa.entities.AnyClass</class>

            <properties>
                <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver" />
                <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost/nome_do_banco" />
                <property name="javax.persistence.jdbc.user" value="postgres" />
                <property name="javax.persistence.jdbc.password" value="root" />

                <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQL94Dialect" />
                <property name="hibernate.show_sql" value="true" />
                <property name="hibernate.format_sql" value="true" />

                <property name="hibernate.hbm2ddl.auto" value="update" />

            </properties>
        </persistence-unit>
    </persistence>
    ```

5. Criar um método main

    ```java
    public static void main(String[] args) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Preencher o nome aqui");
            EntityManager em = emf.createEntityManager();
        }
    ```

- Escrever SQL no código Java é trabalhoso e precisa de manutenção contínua
- A JPA é um ORM (*Object Relacional Mapper*) Java
    - Um ORM mapeia as classes para tabelas e gera o SQL de forma automática
- Para inicializar a JPA, é preciso definir um arquivo de configuração, chamado **persistence.xml**
    - Nele, colocamos as configurações mais importantes, como o *driver* e os dados da conexão
- A classe `Persistence` lê a configuração e cria uma `EntityManagerFactory`
- Podemos usar a JPA para gerar e atualizar as tabelas no banco de dados
- Uma entidade deve usar as anotações `@Entity` e `@Id`
    - `@GeneratedValue` não é obrigatório, mas pode ser útil para definir uma chave *auto-increment*

    ## Persistindo

    ```java
    Conta conta = new Conta();
    conta.setAgencia(123);
    conta.setNumero(15423);

    em.getTransaction().begin();
    em.persist(conta);
    em.getTransaction().commit();
    ```

    Note que foi necessário criar uma transação para realizar a persistência no banco de dados. O que é uma transação?

    A transação é um mecanismo para manter a consistência das alterações de estado no banco, visto que todas as operações precisam ser executadas com sucesso, para que a transação seja confirmada.

    ## O estado Managed

    Quando fazemos um método `find()` o objeto ainda é gerenciado pelo hibernate, ou seja, qualquer alteração, vai mudar, por que ele conhece os dados originais que vieram do banco de dados. Daí ela vai realizar as alterações assim que eu comitar, automaticamente.

     

    ```java
    Conta contaSemSaldo = em.find(Conta.class, 2L);

    em.getTransaction().begin();

    contaSemSaldo.setTitular("Ray");
    contaSemSaldo.setSaldo(600.0);

    em.getTransaction().commit();
    ```

    > Quando fazemos um find() no EntityManager, a JPA e o Hibernate buscarão no banco e criarão um objeto tipo Conta para ser devolvido, representando o registro buscado no database.
    Essa Conta devolvida ainda mantém uma referência, então a JPA ainda a conhece mesmo após a devolução. Sendo assim, costuma-se dizer que esta entidade Conta se encontra no estado Managed, ou seja, gerenciado pela JPA.
    Portanto, como os dados originais do banco são conhecidos, quando fizermos qualquer alteração dentro dessa entidade e commitarmos a transação, haverá a sincronização automática.
    A JPA verificará cada atributo para saber se houve alteração no titular, na agência, número ou saldo. Se houver em algum deles, um update será disparado com a mudança. Este estado Managed também é alcançável quando fazemos um persist() além de find().

    Sobre o estado Managed, podemos afirmar que:

    A entidade nesse estado está automaticamente sincronizada com o banco. A característica do estado *Managed* é a sincronização automática.

    ## O estado Detached

    O estado Detached é quando não há mais sincronização automática, meio morto já. Tipo, antes, qualquer alteração antes do commit, mudaria automaticamente, porém, aqui, se a gente fechar a conexão, já era, ele vai pro estado Detached.

    ```java
    em.getTransaction().begin();

    em.persist(conta);

    em.getTransaction().commit();

    //fechando a transação
    em.close();

    // não será alterado, pois já está em estado Detached
    conta.setSaldo(500.0);
    ```

    E agora? Como faz pra atualizar caso eu queira?

    A resposta pra isso é simples: Basta criar outro `EntityManager`, pois o antigo já foi fechado, e dá um merge. O merge pega um objeto em estado Detached e transforma ele para Managed novamente.

    ```java
    em.close();

    EntityManager em2 = emf.createEntityManager();
    conta.setSaldo(500.0);

    em2.getTransaction().begin();

    em2.merge(conta);

    em2.getTransaction().commit();
    ```

    Qual a característica de uma entidade no estado Detached?

    A entidade nesse estado possui um ID, apesar de não existir sincronização automática. Ela possui um ID, porém o `EntityManager` que a persistiu já não existe mais (já foi fechado).

    ## O estado Transient

    Objeto sem qualquer vínculo com a JPA. Sua característica é uma conta que existe na memória, possui informações e não tem Id nenhum, mas pode se tornar Managed futuramente.

    ```java
    Conta conta = new Conta();
    conta.setAgencia(656500);
    conta.setNumero(65656);
    conta.setTitular("Whatever there");
    ```

    Para transformar de transient para managed:

    ```java
    // Transient -> Maneged
    em.persist(conta);
    ```

    ## O estado Removed

    O estado removed significa que ele não está mais no context da JPA. Diferente do Detached, esse aqui, ao estar em estado Removed, ele, por sua vez, vai remover do banco de dados também!

    ```java
    // Maneged -> Removed
    em.remove(conta);
    ```

     Sobre o estado Removed, podemos afirmar que:

     A entidade nesse estado possui um ID, apesar de não existir sincronização automática e não possuir registro no banco. A entidade já foi *Managed* um dia e portanto possui ID. Diferente do *Detached*, uma entidade *Removed* não possui registro no banco.

    ## Resumo

    - A JPA sincroniza o estado de uma entidade gerenciada com o banco de dados
        - Isto é, o SQL será gerado baseado na diferença entre a entidade em memória e o banco de dados
        - Para essa sincronização acontecer, a entidade precisa estar gerenciada (*Managed*)
    - Os estados de uma entidade são: *Managed*, *Detached*, *Transient* e *Removed*
    - Os métodos do `EntityManager`, como `persist`, `merge` ou `remove` alteram o estado da entidade

    ## Relacionamentos

    ### @ManyToOne

    - Relacionamentos entre entidades precisam ser configurados pelas anotações no atributo que define o relacionamento na classe
    - Um relacionamento do tipo *Muitos-para-Um* deve usar anotação a `@ManyToOne`
        - A anotação `@ManyToOne` causa a criação de uma chave estrangeira
    - A JPA carrega automaticamente o relacionamento ao carregar a entidade

    ### @ManyToMany

    Quando criamos um relacionamento `@ManyToMany` a JPA/Hibernate irá mapeá-lo para tabelas no mundo relacional. Assinale a alternativa que descreve corretamente a forma que ela fará esse mapeamento.

    Criando uma tabela de relacionamento. Será criada uma tabela de relacionamento para as duas tabelas.

    ### @OneToOne

    O @OneToOne presente na classe Cliente, por padrão, não coloca essa restrição constrente nas tabelas. Neste mesmo código, aplicaremos o comportamento através da anotação @JoinColumn() passando o atributo unique como true, o que tornará única a chave estrangeira, impedindo outros relacionamento além da Conta.

    Por padrão, quando temos um relacionamento @OneToOne, ainda não obtemos a restrição que é esperada por um relacionamento @OneToOne.

    O que devemos fazer para solucionar esse problema?

    Colocar a anotação `@JoinColumn(unique=true)` no relacionamento. A anotação `@JoinColumn` só funciona na criação do *schema*, portanto é necessário deletar o banco e criá-lo novamente.

    ---

    ### Resumo

    - Como relacionar uma entidade com uma coleção de uma outra entidade
        - Para tal, temos as anotações `@OneToMany` e `@ManyToMany`, dependendo da cardinalidade
        - Um relacionamento `@ToMany` precisa de uma tabela extra para a representação no banco de dados
    - Aprendemos também como relacionar uma entidade com uma outra entidade
        - Para tal, temos as anotações `@OneToOne` e `@ManyToOne`, dependendo da cardinalidade
    - Ao persistir uma entidade, devemos persistir as entidades transientes do relacionamento

    ## Querys

    Nós não usamos mais SQL, por que queremos nos distanciar do mundo relacional. Queremos nos aproximar do mundo orientado a objetos, por isso, não usamos SQL e sim JPQL.

    Exemplo de SQL:

    ```java
    String query = "select * from movimentacao where conta_id = 2";
    ```

    A mesma com JPQL

    ```java
    String jpql = "select m from Movimentacao m where m.conta.id = 2";
    ```

    Para executar a JPQL:

    ```java
    String jpql = "select m from Movimentacao m where m.conta.id = 2";
    Query query = em.createQuery(jpql);
    List<Movimentacao> resultList = query.getResultList();
    ```

    Exemplo com código:

    ```java
    //criando conta com id 4
    Conta conta = new Conta();
    conta.setId(4L);

    //selecione todas as movimentacoes da conta que será passada como parametro
    String jpql = "select m from Movimentacao m where m.conta = :conta";

    //criando query...
    Query query = em.createQuery(jpql);
    //setando parametro
    query.setParameter("conta", conta);
    //executando e recuperando a query em lista
    List<Movimentacao> resultList = query.getResultList();

    //imprimindo
    resultList.forEach(movimentacao -> {
    		System.out.println("Descrição: " + movimentacao.getDescricao());
        System.out.println("Tipo: " + movimentacao.getTipoMovimentacao());
    });
    ```

    Quando trabalhamos com bancos relacionais, usamos a linguagem SQL para manipular os dados.

    Nesta aula, conhecemos a JPQL e as suas diferenças do SQL. Sabendo disso, podemos afirmar que:

    JPQL é orientado a objetos, enquanto SQL não.
    De fato, JPQL é feita para abstrair o mundo relacional, permitindo com que os desenvolvedores se preocupem apenas com objetos.

    - Como executar *queries* com JPA, usando **JPQL**
        - A linguagem JPQL é bem parecida com SQL, no entanto orientada a objetos
        - Com JPQL, usamos as classes e atributos (no lugar das tabelas e colunas) para definir a pesquisa
        - O objeto do tipo `javax.persistence.Query` encapsula a *query*
        - `javax.persistence.TypedQuery` é a versão *tipada* do `javax.persistence.Query`

    ## Atividade proposta

    Dado que todo(a) aluno(a) tem um email (máximo de 30 caracteres),nome (máximo de 30 caracteres) e idade (entre 1 e 100). Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

    Dentro do bootcamp temos também um conjunto de avaliações que são respondidas pelas pessoas. Toda avaliação tem um título e uma descrição do que precisa ser feito. Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

    Cada aluno(a) responde uma ou mais avaliações que chamamos de cognitive walkthrough, ela tem que descrever os passos da solução dela para determinada situação problema. Toda resposta tem um campo aberto para que a pessoa consiga descrever a solução dela. É necessário que toda resposta seja linkada com a pessoa que a respondeu e também com a avaliação relativa àquela resposta. Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

    Além de responder sua avaliação, a pessoa também responde um outro formulário onde ela corrige sua resposta em função da resposta de um mentor(a). Essa correção sempre tem uma nota de 1 a 10 e está linkada com a avaliação respondida pela própria pessoa. Como você vai modelar essa classe e configurá-la para que possa ser utilizada pelo Hibernate?

    Agora que você modelou e mapeou as classes para que possam ser utilizadas pelo hibernate, temos desafios extras.

    Quais são os passos para salvar para salvar um(a) aluno(a)?

    Quais são os passos para salvar para salvar uma avaliação?

    Quais são os passos para salvar uma resposta de um(a) aluno(a)?

    Quais são os passos para salvar a auto correção de um(a) aluno(a)?

    Caso você precise carregar uma auto correção e tenha que descobrir o nome do(a) aluno(a) que fez, como você faria? Algum ponto de atenção que deveria ter?

    Caso você precise carregar as respostas de um(a) aluno(a) a partir do objeto da classe Aluno, como você faria? Algum ponto de atenção?

    ### Resposta

    1. Criar e Configurar o projeto com maven, criar a pasta `META-INF`, configurar o arquivo `persistense.xml` e adicionar as dependências necessárias. 
    2. Para criar a entidade Aluno, eu criaria uma classe chamada Aluno com os atributos nome, email, idade e, também, adicionaria um id a ela. Para configurar ela com o hibernate, anotaria a classe com `@Entity` para que a nossa classe seja reconhecida pelo Hibernate, no campo de id, anotaria com `@Id` e deixaria o id ser gerado automaticamente pelo banco com `@GeneratedValue(strategy = GenerationType.AUTO)`. Para limitar os tamanhos de nome e idade, usaria a anotação `@Column(length = 30)` com isso, configuraria os campos para `varchar(30)`. Para idade, usaria `@Column(columnDefinition = "TINYINT")` forçando para o menor tipo possível;
    3. Para as avaliações, criaria uma classe Avaliacao e mapearia ela da mesma forma, com  `@Entity`, com id gerado automaticamente, e teriam mais 2 outros campos que seriam `titulo` e `descricao`;
    4. Criar classe Resposta com os padrões de mapeamento citados acima com id gerada automaticamente e nela terá dois relacionamentos `@ManyToOne` referenciando as classes Aluno e Avaliacao, para que ambos estejam linkados diretamente com a resposta;
    5. Criar class Correcao com os padrões de mapeamento citados no início com id também, com a nota, com uma descrição(pra dizer onde errou ou acertou) e com um relacionamento `@OneToOne` com a entidade Resposta. Também anotaria esse relacionamento com `@JoinColumn(unique = true)` afim de garantir que  a correção será única.
    6. Para salvar, configuraria nossa Factory buscando nosso `persistenceUnitName` declarado no `persistence.xml`. Com a factory pronta, instanciaria o `EntityManager`;
    7. Com `EntityManager` configurado, basta instanciar e preencher os dados do aluno, abrir o contexto de transação, persistir o aluno, dar um commit na transação e fechá-la.
    8. Instanciar e preencher uma avaliacao,  abrir o contexto de transação, persistir a avaliacao, dar um commit na transação e fechá-la.
    9. Para salvar uma resposta de um aluno, precisamos que a Avaliacao e o Aluno em questão estejam persistidos no banco de dados. Após salvá-los ou recuperá-los, basta setá-los no objeto Resposta e aí então persistir e commitar.
    10. Para salvar uma correção de um aluno, precisamos que a Resposta em questão esteja salva no banco de dados. Após salvá-la ou recuperá-la, basta setá-la no objeto Correcao.
    11. Para carregar uma Correção e o aluno em questão, usaria a Query JPQL que trouxesse a correção baseada no Id do parâmetro. No entanto, dessa forma, vai trazer tudo junto da correção, desde a resposta, até a avaliação. Caso fosse um objeto maior, carregaria várias outras coisas desnecessárias.
    12. Para carregar as Respostas de um aluno baseado no Objeto Aluno, eu usaria uma Query JPQL que trouxesse uma lista de Respostas baseadas no aluno, passado como parâmetro. Atenção aqui é a mesma acima, vai trazer todos os objetos que estão relacionados com ele.
