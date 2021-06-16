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