# Introdução ao SQL com MySQL: Manipule e consulte dados

### Criando e deletando uma base de dados

```sql
#cria um banco de dados / schema chamado 'sucos'
CREATE DATABASE sucos;

#Apaga o banco de dados / schema
DROP DATABASE sucos;
```

### Criando uma tabela

```sql
CREATE TABLE TB_CLIENTES
( CPF VARCHAR (11) ,
NOME VARCHAR (100) ,
ENDERECO1 VARCHAR (150) ,
ENDERECO2 VARCHAR (150) ,
BAIRRO VARCHAR (50) ,
CIDADE VARCHAR (50) ,
ESTADO VARCHAR (2) ,
CEP VARCHAR (8) ,
IDADE SMALLINT,
SEXO VARCHAR (1) ,
LIMITE_CREDITO FLOAT ,
VOLUME_COMPRA FLOAT ,
PRIMEIRA_COMPRA BIT);
```

### Dropar uma tabela

```sql
DROP TABLE TB_CLIENTES;
```

### Inserir em uma tabela

```sql
#inserindo um dado
INSERT INTO tbproduto (PRODUTO, NOME, EMBALAGEM, TAMANHO, SABOR, PRECO_LISTA) VALUES (
'1040107', 'Light - 350 ml - Melância','Lata', '350 ml', 'Melância', 4.56);

#inserindo vários dados
INSERT INTO tbproduto (PRODUTO,  NOME, EMBALAGEM, TAMANHO, SABOR,PRECO_LISTA) VALUES
('1037797', 'Clean - 2 Litros - Laranja','PET', '2 Litros', 'Laranja', 16.01),
('1000889', 'Sabor da Montanha - 700 ml - Uva','Garrafa', '700 ml', 'Uva', 6.31),
('1004327', 'Videira do Campo - 1,5 Litros - Melância','PET', '1,5 Litros', 'Melância', 19.51);
```

### Update

```sql
UPDATE tbproduto SET EMBALAGEM = 'Lata', PRECO_LISTA = 2.46
WHERE PRODUTO = '544931';

UPDATE tbproduto SET EMBALAGEM = 'Garrafa'
WHERE PRODUTO = '1078680';
```

### Deletar campo na tabela

```sql
DELETE FROM tbproduto WHERE PRODUTO = '1078680';
```

### Inserindo chave primária para uma tabela

```sql
ALTER TABLE tbproduto ADD PRIMARY KEY (PRODUTO);
```

### Criando um novo campo pra tabela

```sql
ALTER TABLE tbcliente ADD COLUMN (DATA_NASCIMENTO DATE);
```

### Selects

```sql
#selecionando tudo 
SELECT * FROM tbcliente;

#Apenas cpf e nome
SELECT CPF, NOME FROM tbcliente;

#Apenas cpf e nome, limitando 5 resultados
SELECT CPF, NOME FROM tbcliente LIMIT 5;

#Cria uma view dando nome aos campos
SELECT CPF AS CPF_CLIENTE, NOME AS NOME_CLIENTE FROM tbcliente;

#Selecione todos os campos da table produtos onde o produto é '544931'
SELECT * FROM tbproduto WHERE PRODUTO = '544931';

#Selecione onde idade é igual a 22
SELECT * FROM tbcliente WHERE IDADE = 22;

#Selecione onde idade maior q 22
SELECT * FROM tbcliente WHERE IDADE > 22;

#Selecione onde idade é menor que 22
SELECT * FROM tbcliente WHERE IDADE < 22;

#Selecione onde idade é menor ou igual que 22
SELECT * FROM tbcliente WHERE IDADE <= 22;

#Selecione onde idade não é 22. Todos menos 22...
SELECT * FROM tbcliente WHERE IDADE <> 22;

#Selecione onde nome é maior ou igual a Fernando. Ordem alfabética.
SELECT * FROM tbcliente WHERE NOME >= 'Fernando Cavalcante';

#Selecione onde nome é diferente a Fernando. Ordem alfabética.
SELECT * FROM tbcliente WHERE NOME <> 'Fernando Cavalcante';

#Não funciona para números quebrados. <= ou >=
SELECT * FROM tbproduto WHERE PRECO_LISTA <= 16.008;

#Selecione onde PRECO_LISTA está entre 16.007 e 16.009.
SELECT * FROM tbproduto WHERE PRECO_LISTA BETWEEN 16.007 AND 16.009;

#FILTROS DE DATA
SELECT * FROM tbcliente WHERE DATA_NASCIMENTO = '1995-01-13';

SELECT * FROM tbcliente WHERE DATA_NASCIMENTO > '1995-01-13';

SELECT * FROM tbcliente WHERE DATA_NASCIMENTO <= '1995-01-13';

SELECT * FROM tbcliente WHERE YEAR(DATA_NASCIMENTO) = 1995;

SELECT * FROM tbcliente WHERE MONTH(DATA_NASCIMENTO) = 10;

#FILTROS COMPOSTOS

#igual BETWEEN 
SELECT * FROM tbproduto WHERE PRECO_LISTA >= 16.007 AND PRECO_LISTA <= 16.009;

#Selecione clientes que têm idade entre 18 e 22
SELECT * FROM tbcliente WHERE IDADE >= 18 AND IDADE <= 22;

#Selecione clientes que têm idade entre 18 e 22 e sexo Masculino
SELECT * FROM tbcliente WHERE IDADE >= 18 AND IDADE <= 22 AND SEXO = 'M';

#Selecione clientes onde a cidade é Rio ou o bairro é Jardins
SELECT * FROM tbcliente WHERE cidade = 'Rio de Janeiro' OR BAIRRO = 'Jardins';

#Selecione clientes que têm idade entre 18 e 22 e sexo Masculino ou 
onde a cidade é Rio ou o bairro é Jardins
SELECT * FROM tbcliente WHERE (IDADE >= 18 AND IDADE <= 22 AND SEXO = 'M')
 OR (cidade = 'Rio de Janeiro' OR BAIRRO = 'Jardins');
```

### Atividade proposta

Somos muitos alunos e alunas aqui no Bootcamp. Em função de todas as avaliações respondidas, muitos dados são gerados. E agora temos alguns desafios para você:

Dado que todo(a) aluno(a) tem um email(máximo de 30 caracteres),nome(máximo de 30 caracteres) e idade(entre 1 e 100). O que você faria para representar essa estrutura no banco?

- O que você precisa fazer agora para inserir novos(as) alunos(as) nessa tabela?
- E para listar tudo que está registrado?
- E para apagar um registro pelo email?
- Agora você precisa buscar todos os(as) alunos(as) que tem Zup no email. Como você faria?
- E para fechar é necessário que alunos e alunas sejam listados pela sua idade em ordem crescente.

### Minha resposta: 

1. Criar a base de dados do Bootcamp para que possamos organizar e agrupar nossas tabelas. Comando sql: `CREATE DATABASE Bootcamp;`
2. Criar a tabela alunos: 

    ```sql
    CREATE TABLE `alunos` (
      `id` bigint NOT NULL,
      `email` varchar(30) DEFAULT NULL,
      `nome` varchar(30) DEFAULT NULL,
      `idade` tinyint DEFAULT NULL,
      PRIMARY KEY (`id`)
    );
    ```

    Para a criação da tabela, criaria, também, uma chave primária para cada aluno ter seu identificador. Para garantir que o nome e email tenham 30 caracteres, usaria `varchar(30)` e para idade, usaria um `tinyint`, que é o menor tipo de inteiro;

3. Para inserção dos alunos na tabela, usaria o seguinte SQL de inserção. Aqui está um exemplo:

    ```sql
    INSERT INTO `alunos` (`id`, `email`, `nome`, `idade`)
    VALUES (1, 'ray@zup.com.br', 'ray', 23);
    ```

4. Para listar:

    ```sql
    SELECT * FROM `alunos`;
    ```

5. Para deletar, eu escolheria deletar pela Primary Key `id`. Dessa forma, garanto que apenas uma linha será afetada, visto que a Primary Key é única. Aqui está um exemplo:

    ```sql
    DELETE FROM `alunos` WHERE id = 1;
    ```

6. Para listar todos os alunos com email Zup, usaria o seguinte SQL:

    ```sql
    SELECT * FROM `alunos` WHERE `email` LIKE '%zup%';
    ```

    Dessa forma, encontrará qualquer valor que contiver 'zup' em qualquer posição.

7. Para listagem dos alunos por idade em ordem crescente:

     

    ```sql
    SELECT * FROM `alunos` ORDER BY `idade` ASC;
    ```

    Em que o `ORDER BY` irá ordenar todos os alunos por `idade` e o `ASC` indica que será em ordem crescente.
