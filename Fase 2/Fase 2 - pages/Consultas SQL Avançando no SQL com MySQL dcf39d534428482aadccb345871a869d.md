# Consultas SQL: Avançando no SQL com MySQL

### SELECT DISTINCT

Irá retornar somente linhas com valores diferentes;

Por exemplo: Na minha tabela de produtos, executando o SQL abaixo, eu tenho os seguintes resultados:

```sql
SELECT * FROM products
```

[Tabela de produtos](https://www.notion.so/927ece7eb3ff45489b5cad38cf701ff4)

Note que existem 2 registros de *SSD 256,* mas ambos com preços diferentes.

Caso eu use o `SELECT DISTINCT`, por nome, vai me retornar apenas 1 *SSD 256,* pois o campo se REPETE. E o `DISTINCT` traz os campos que NÃO SE REPETEM, de acordo com atributo passado. No nosso exemplo, o atributo foi o *Nome.* Se tivesse sido por preço, retornaria todos, **pois nenhum preço se repete.**

```sql
SELECT DISTINCT nome FROM products
```

[Tabela de produtos com DISTINCT pelo nome](https://www.notion.so/5bf5602c85714c16a2c55f37ec178f4d)

### LIMIT

```sql
#retorna todos (100)
SELECT * FROM tabela_de_produtos;

#retorna os primeiros 5 (5 rows)
SELECT * FROM tabela_de_produtos LIMIT 5;

#retorna, a partir do segundo, os próximos 3 (incluido ele). No caso: 2*, 3 e 4*
SELECT * FROM tabela_de_produtos LIMIT 2,3;

SELECT * FROM tabela_de_produtos LIMIT 0,2;
```

### ORDER BY

```sql
#Ordenando por preço (default é ASC [Crescrente])
SELECT * FROM tabela_de_produtos ORDER BY PRECO_DE_LISTA;

#Ordenando por preço setando DES [Decrescente]
SELECT * FROM tabela_de_produtos ORDER BY PRECO_DE_LISTA DESC;

#Ordenando por embalagem DESC e nome ASC.
#O primeiro
SELECT * FROM tabela_de_produtos ORDER BY EMBALAGEM DESC, NOME_DO_PRODUTO ASC;
```

### GROUP BY

Agrupar a resposta é juntar campos que são repetidos e no caso dos campos numéricos quando eu faço essa junção eu posso aplicar uma fórmula matemática, que pode ser soma, média, máximo, mínimo valor.

Ele vai agrupar e realizar algo. Soma, média, count.. enfim.

Qual é o total de limite de crédito que eu tenho em SP? RJ? Ou outros estados? Como faço isso?

Exemplo:

```sql
 SELECT ESTADO, LIMITE_DE_CREDITO FROM tabela_de_clientes;
```

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled.png)

O SQL acima, retorna linha por linha, ou seja, de todos os clientes e por isso repete. Mas não é esse nosso objetivo. Nosso objetivo é saber a SOMA de todos os LIMITES DE CRÉDITO de cada estado. Para isso, usamos o `GROUP BY` que irá *AGRUPAR* todos os estados em *UMA ÚNICA QUERY* . 

```sql
#Quando usamos SUM, precisamos de usar AS para apelidar o novo resultado
SELECT ESTADO, SUM(LIMITE_DE_CREDITO) AS LIMITE_TOTAL 
FROM tabela_de_clientes GROUP BY ESTADO;
```

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%201.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%201.png)

Dessa forma, descobrimos o limite total de cada estado. E, olha só! O RJ tem mais limite que SP. Que mentira kkkk

### HAVING

É tipo um IF. Por exemplo:

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%202.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%202.png)

~~kkkk o cara ali~~

Note que o `GROUP BY` está realizando uma soma do campo X, ou seja, A e A virou apenas 1 A, e o Y ali foi somado, retornando 3. Isso é o `GROUP BY`. Certo. O `HAVING` entra depois do `GROUP BY` ele joga uma CONDIÇÃO no RESULTADO do `GROUP BY` Então, seria: 

$$IF(GROUP -BY-RESULT) > 6  = HAVING $$

Note que o `HAVING` impôs uma condição. A SOMA DE Y PRECISA SER MAIOR OU IGUAL A 6.

Então não seria melhor usar um `WHERE` ? Visto que o WHERE é uma condição...

```sql
SELECT ESTADO, SUM(LIMITE_DE_CREDITO) AS SOMA_LIMITE FROM tabela_de_clientes
WHERE SOMA_LIMITE > 900000
GROUP BY ESTADO;
```

Ótima pergunta e a resposta é: **Não**.  

O `HAVING` REALIZA O CÁLCULO DO RESULTADO DE UM `GROUP BY`. O `WHERE` não consegue realizar condições de um `GROUP BY` pois o AGRUPAMENTO AINDA NÃO ACONTECEU e, portanto, não é possível.

Nosso objetivo é **primeiro agrupar e depois filtrar o agrupamento**. Para isso existe o `HAVING` Portanto, usaríamos o SQL:  

```sql
SELECT ESTADO, SUM(LIMITE_DE_CREDITO) AS SOMA_LIMITE FROM tabela_de_clientes
GROUP BY ESTADO HAVING SUM(LIMITE_DE_CREDITO) > 900000;
```

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%203.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%203.png)

### CASE

Substituir a saída de dados, por uma classificação, um texto personalizado

```sql
SELECT NOME_DO_PRODUTO, PRECO_DE_LISTA,
CASE 
   WHEN PRECO_DE_LISTA >= 12 THEN 'PRODUTO CARO'
   WHEN PRECO_DE_LISTA >= 7 AND PRECO_DE_LISTA < 12 THEN 'PRODUTO EM CONTA'
   ELSE 'PRODUTO BARATO' 
END AS STATUS_PRECO 
FROM tabela_de_produtos;
```

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%204.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%204.png)

### JOIN

Juntar uma ou mais tabelas em um único comando SQL;

Precisam ser feitos em campos comuns entre as tabelas.

### INNER JOIN

Retorna apenas os que tem dados correspondentes:

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%205.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%205.png)

### LEFT JOIN

Vai trazer todos da esquerda (primeira tabela citada na query SQL) e apenas os correspondentes da direita. 

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%206.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%206.png)

### RIGHT JOIN

Da mesma forma do LEFT JOIN, esse traz todos da direita

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%207.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%207.png)

### FULL JOIN

Traz geral kkk

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%208.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%208.png)

### UNION

Meio que dá um select `DISTINCT` mas com DUAS ou MAIS tabelas.

```sql
SELECT DISTINCT BAIRRO FROM tabela_de_clientes
UNION
SELECT DISTINCT BAIRRO FROM tabela_de_vendedores;
```

Quando eu quero listar todos os registros de duas ou mais tabelas, listando inclusive os repetidos, qual UNION eu uso?

R: UNION ALL: Esta irá mostrar todos repetindo os valores.

```sql
#junta sem DISTINCT
SELECT DISTINCT BAIRRO FROM tabela_de_clientes
UNION ALL
SELECT DISTINCT BAIRRO FROM tabela_de_vendedores;
```

### IN

Selecione (da tabela 1) tudo que tiver na tabela Y

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%209.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%209.png)

### VIEW

Criamos um select padrão e salvamos ele como uma VIEW. Essa view, será o resultado desse select. 

Agora podemos usar select * from view_criada e, por detrás dos panos, ele rodará o SQL de criação. Meio que um atalho. 

Podemos juntar a view com outras tabelas, outras views e etc. Normal. 

![Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%2010.png](Consultas%20SQL%20Avanc%CC%A7ando%20no%20SQL%20com%20MySQL%20dcf39d534428482aadccb345871a869d/Untitled%2010.png)

## Atividade proposta

Somos muitos alunos e alunas aqui no Bootcamp. Em função de todas as avaliações respondidas, muitos dados são gerados. E agora temos alguns desafios para você:

Dado que todo(a) aluno(a) tem um email (máximo de 30 caracteres),nome (máximo de 30 caracteres) e idade (entre 1 e 100). O que você faria para representar essa estrutura no banco?

Dentro do bootcamp temos também um conjunto de avaliações que são respondidas pelas pessoas. Toda avaliação tem um título e uma descrição do que precisa ser feito. O que você faria para representar essa estrutura no banco?

Cada aluno(a) responde uma ou mais avaliações. Toda resposta tem um campo aberto para que a pessoa consiga descrever a solução dela. É necessário que toda resposta seja linkada com a pessoa que a respondeu e também com a avaliação relativa àquela resposta. O que você faria para representar essa estrutura no banco de dados?

Além de responder sua avaliação, a pessoa também responde um outro formulário onde ela corrige sua resposta em função da resposta de um mentor(a). Essa correção sempre tem uma nota de 1 a 10 e está linkada com a avaliação respondida pela própria pessoa. O que você faria para representar essa estrutura no banco de dados?

Precisamos saber todo mundo que respondeu uma avaliação com um título específico? 

Precisamos saber quantas respostas foram dadas por avaliação

Precisamos da nota média da autocorreção por avaliação

### Resposta

1. Criar a base de dados do Bootcamp para que possamos organizar e agrupar nossas tabelas. Comando sql: `CREATE DATABASE Bootcamp` ;
2. Criar uma tabela com o comando `CREATE TABLE` com nome `alunos`definindo os campos de nome, email e idade. criaria, também, uma chave primária para cada aluno ter seu identificador. Para garantir que o nome e email tenham 30 caracteres, usaria `varchar(30)` e para idade, usaria um `tinyint`, que é o menor tipo de inteiro;
3. Para avaliações, criaria outra tabela também com `CREATE TABLE` com chave primária auto gerada pelo banco, título e descrição no tipo `Text` que parece o adequado.
4. Para o relacionamento entre alunos e avaliações, criaria uma tabela separada, contendo a chave primária, e as 2 chaves estrangeiras correspondentes às chaves primárias da tabela de **alunos** e **avaliação**. Dessa forma, consigo recuperar os dados correspondentes futuramente com algum tipo de `JOIN`. Para criação das chaves estrangeiras, usaria o comando `ALTER TABLE ... ADD FOREIGN KEY ... REFERENCES (..)`;
5. Para registrar que um aluno respondeu uma avaliação, também criaria outra tabela com `CREATE TABLE` com chave primária, nota do tipo `tinyint`, pois é o menor tipo pra inteiro no mysql e uma chave estrangeira referente à chave primária da tabela de relacionamento entre alunos e avaliações;
6. Para saber todo mundo que respondeu algum título específico, selecionaria o nome da tabela de alunos, seguida de um `INNER JOIN` com a tabela de relacionamento entre alunos e avaliações para juntar os alunos que realizaram avaliações. Em seguida usaria outro `INNER JOIN` na tabela de avaliações, para recuperar as avaliações. Por fim, usaria a clausura `WHERE` com `LIKE`. Para ficar mais claro, este seria o SQL: 

    ```sql
    SELECT nome from tb_alunos 
    inner join tb_walkthough on tb_alunos.id = tb_walkthough.id_aluno 
    inner join tb_avaliacoes on tb_walkthough.id_avaliacao = tb_avaliacoes.id
    where tb_avaliacoes.titulo like '%ANY TITLE HERE%'; 
    ```

7. Para saber quantas respostas foram dadas por avaliação, usaria um `SELECT COUNT` na nossa tabela que registra que um aluno respondeu uma avaliação, pois contaria quantos registros têm na tabela, e a nossa tabela registra todas as respostas realizadas.

    ```sql
    select COUNT(*) from tb_respostas;
    ```

8. Para saber da nota média da autocorreção por avaliação, usaria o `AVG` nas notas da tabela de respostas. O `AVG` vai calcular a média, que é justamente o que é pedido

    ```sql
    select avg(nota) as media from tb_respostas;
    ```