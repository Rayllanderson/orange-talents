# Tests

```java
public class RespostaRepository {

    private EntityManager manager;

    public RespostaRepository(EntityManager manager){
        this.manager = manager;
    }

    public Collection<Resposta> buscaRespostas(Long idAluno){
        return manager.createQuery("select r from Resposta r where r.aluno.id = :idAluno", Resposta.class)
                .setParameter("idAluno",idAluno)
                .getResultList();
    }
}
```

Descreva os testes automatizados que você vai escrever para aumentar a confiabilidade deste código.

1. Primeiro eu criaria um método setup para carregar todas as configurações necessárias. Um exemplo, é instanciar o entity manager e passar no construtor do reposta repository. Também abro uma transação aqui nesse método. Ele é anotado com @Before para que seja configurado isso toda vez que cada teste rodar, dessa forma, teremos testes isolados, sem que um atrapalhe o outro. 
2. Crio um método pra dar rollback a cada finalização de teste com @After. Dessa forma, não "sujo" o banco de dados.
3. Começando os testes, eu realizaria o teste pra verificar se a lista retorna alguma coisa de fato, então eu faço todo processo necessário pra salvar uma resposta com um aluno e então começo o teste em si, verificando se a lista não está vazia e se possui um elemento.
4. Testo também se ela me retorna uma lista vazia quando não salvo nenhuma reposta.
5. Outro teste seria eu salvar uma resposta do aluno João e no método buscaResposta, eu usar o ID da Maria, que não tem resposta salva. Daí vejo se realmente vai me trazer uma lista vazia de acordo com o id passado.

Descreva quais testes você faria para o método abaixo.

```java
public boolean aceitaPalavra(String str){

if(str == null || str.length() < 5){

return false;

}

char primeiroCaractere = str.charAt(0);

int tamanho = str.length();

if (Character.isLetter(primeiroCaractere)

&& (ultimoCaractere == 's' || tamanho >= 10)) {

return true;

} else {

return false;

}

}
```

```java
(1,5), (2,6), (3,7) 
(2,4)
(3,4)

(2, 4, 3,7)

1 - V	V	V	= V		  
2 - V	V	F	=	V			
3 - V	F	V	=	V			
4 - V	F	F	=	F		
5 - F	V	V	=	F				
6 - F	V	F	=	F				
7 - F	F	V	=	F		
8 - F	F	F	=	F		

```

  

1. Para o primeiro IF, testaria passando a String nula, String menor que 5, String igual a 5 e String maior que 5. 
2. Para o segundo IF, realizaria 4 testes. Vou simplificar, mas a explicação é assim: V	F	V significa que o primeiro V é o nosso `Character.isLetter(primeiroCaractere)` em seguida o F, que é a nossa segunda expressão booleana, `ultimoCaractere == 's'`e, por fim, o último V que indica `tamanho >= 10`. Considerando esses casos, eu testaria as seguintes expressões:  `V V F`, `V F F`, `V F V`  e  `F F V`. Pra ficar claro, vou fazer o primeiro exemplo, o `V V F`. Passaria uma String que desse verdadeira nas 2 primeiras expressões, por exemplo: "cartões" que `Character.isLetter(primeiroCaractere)` = verdadeiro, `ultimoCaractere == 's'` = verdadeiro, e `tamanho >= 10` é falso. e seguiria esse mesmo fluxo.