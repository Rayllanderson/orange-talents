# Desafio 2

# **Cadastro de novo usuário**

### **Necessidades**

- Precisamos saber o instante do cadastro, login e senha.

### **Restrições**

- O instante não pode ser nulo e não pode ser no futuro
- O login não pode ser em branco ou nula
- O login precisa ter o formato do email
- A senha não pode ser branca ou nula
- A senha precisa ter no mínimo 6 caracteres
- A senha deve ser guardada usando algum algoritmo de hash da sua escolha.

### **Resultado esperado**

- O usuário precisa estar criado no sistema
- O cliente que fez a requisição precisa saber que o usuário foi criado. Apenas um retorno com status 200 está suficente.
- Em caso de falha de validação status 400

## Como eu faria...

1. Começaria pelo controller, anotado com @RestController, recebendo os dados via Post, com o corpo anotado com @Valid para validar os dados. O objeto de request em questão, será um DTO.
2. Anotaria as anotações necessárias do bean validation para que os campos fossem validados.
3. Para a questão do hash de senha, pediria um para o Spring no controller e passava ele no método do DTO que vai transformar o DTO em model, dessa forma, lá dentro ele vai setar a senha com o hash recebido.
4. Criaria a entidade User para persistir com a JPA, portanto, anotaria a classe modelo com as anotações necessárias do JPA, além da bean validation. O construtor teria os atributos obrigatórios. 
5. Com a classe de modelo em mãos, basta persistir usando um entity manager que instanciamos no controller.
6. Para retornar 400, criarei um Controller advice, que irá tratar os erros de validações e retornar 400.

---

# **Não podemos ter dois usuários com o mesmo email.**

### **Necessidades**

- Só pode existir um usuário com o mesmo email.

### **Resultado esperado**

- Status 400 informando que não foi possível realizar um cadastro com este email.

### **Antes de começar**

Por favor descreva como você pretende realizar a implementação deste desafio.

Adiciono um validator genérico em forma de anotação. Passando a entidade User no parâmetro da anotação.

Anoto no DTO a anotação

---

# **Cadastro de categorias**

### **Necessidades**

No mercado livre você pode criar hierarquias de categorias livres. Ex: Tecnologia -> Celulares -> Smartphones -> Android,Ios etc. Perceba que o sistema precisa ser flexível o suficiente para que essas sequências sejam criadas.

- Toda categoria tem um nome
- A categoria pode ter uma categoria mãe

### **Restrições**

- O nome da categoria é obrigatório
- O nome da categoria precisa ser único

### **Resultado esperado**

- Categoria criada e status 200 retornado pelo endpoint.
- Caso exista erros de validação, o endpoint deve retornar 400 e o json dos erros.

1. Começaria pelo controller, anotado com @RestController, recebendo os dados via Post, com o corpo anotado com @Valid para validar os dados. O objeto de request em questão, será um DTO.
2. Anotaria as anotações necessárias do bean validation para que os campos fossem validados.
3. Criaria a entidade Categoria para persistir com a JPA, portanto, anotaria a classe modelo com as anotações necessárias do JPA, além da bean validation. O construtor teria os atributos obrigatórios. 
4. Com a classe de modelo em mãos, basta persistir usando um entity manager que instanciamos no controller.
5. Para retornar 400, criarei um Controller advice, que irá tratar os erros de validações e retornar 400.

Para questão da hierarquia das categorias, eu passaria uma categoria na própria categoria, anotada com many to one, indicando que muitas filhas podem ter uma mãe. 

---

## Trabalhando com o usuário logado

1. Com Spring security adicionado, eu iria implementar na entidade User o UserDetails que vai prover informações básicas do usuário.
2. Criaria um Service de autenticação que instanciaria um user repository, que será responsável por recuperar o usuário pelo username, no nosso caso, o login, sendo assim, criaremos um método findByLogin e vamos usar ele para recuperar o usuário.
3. Configuro o service na classe de Configurações gerais de Segurança.
4. Crio uma classe geradora de token e um filtro pra lidar com e token.
5. Adiciono o filtro nas configurações de segurança.
6. Mapeio os endpoints necessários que precisam de autorização.
7. Pra recuperar o usuário logado, utilizado o `@AuthenticationPrincipal` passando, em seguida, o objeto User, o mesmo que implementou o UserDetails.

---

# **Usuário logado cadastra novo produto**

### **Explicação**

Vamos permitir o cadastro de um produto por usuário logado.

### **Necessidades**

- Tem um nome
- Tem um valor
- Tem quantidade disponível
- Características(cada produto pode ter um conjunto de características diferente). [Dá uma olhada na parte de outras características nos detalhes de produtos do mercado livre](https://produto.mercadolivre.com.br/MLB-1279370191-bebedouro-bomba-eletrica-p-garrafo-galo-agua-recarregavel-_JM?variation=48969374724#reco_item_pos=0&reco_backend=navigation&reco_backend_type=function&reco_client=home_navigation-recommendations&reco_id=e55bf74a-9551-42d8-a43d-fb64fa3117d4&c_id=/home/navigation-recommendations/element&c_element_order=1&c_uid=761d5d17-5baf-4fd8-be79-fc65ee66a6fb). Cada característica tem um nome e uma descricao associada.
- Tem uma descrição
- Pertence a uma categoria
- Instante de cadastro

### **Restrições**

- Nome é obrigatório
- Valor é obrigatório e maior que zero
- Quantidade é obrigatório e >= 0
- O produto possui pelo menos três características
- Descrição é obrigatória e tem máximo de 1000 caracteres.
- A categoria é obrigatória

### **Resultado esperado**

- Um novo produto criado e status 200 retornado
- Caso dê erro de validação retorne 400 e o json dos erros

1. Começaria pelo controller, anotado com @RestController, recebendo os dados via Post, com o corpo anotado com @Valid para validar os dados. O objeto de request em questão, será um DTO.
2. Anotaria as anotações necessárias do bean validation para que os campos fossem validados.
3. Criaria a entidade para persistir com a JPA, portanto, anotaria a classe modelo com as anotações necessárias do JPA, além da bean validation. O construtor teria os atributos obrigatórios. 
4. Com a classe de modelo em mãos, basta persistir usando um entity manager que instanciamos no controller.
5. Para retornar 400, criarei um Controller advice, que irá tratar os erros de validações e retornar 400.

Para as características de um produto, eu crio uma entidade chamada Caracteristicas com nome, descrição e um produto anotado com many to one. 

Agora, nos produtos, crio um set de características para que não tenha nenhuma repetida, anotada com one to many.

Crio 2 validadores, um que checa a quantidade de características e o outro que checa se elas são repetidas. 

- Aqui é um cadastro um pouco mais complicado, dado que o novo produto, além de informações de tipos que já existem na linguagem, como nome, quantidade, e descrição, tem também as suas características. Então, na classe que representa os dados de entrada de um novo produto, eu adiciono um atributo do tipo Set<NovaCaracteristicaRequest>. Justamente para representar os dados das características que vão chegar. Eu utilizo o Set aqui porque não queremos características com nomes iguais para o mesmo produto.
- Na classe NovaCaracteristicaRequest eu tenho os atributos que representam a chave e o valor da característica. Preciso criar um equals e hashcode na classe NovaCaracteristicaRequest para que ela possa ser usada em uma coleção do tipo Set. Uso o nome como atributo que define a igualdade entre características.
- Além das características, para representar a associação com a categoria, adiciono o atributo do tipo Integer/Long para receber o id categoria do produto
- Adiciono as annotations de validação em cima de cada atributo. Atenção especial para a utilização do @Size para controlar o mínimo de características. E como colocamos o atributo que representa a coleção de características como uma implementação da interface Set e implementamos o equals e hashcode na NovaCaracteristicaRequest, automaticamente protegemos de nomes duplicados.
- NNDWPKD   Dentro do método do controller eu converto os dados do DTO para a criação de um objeto do tipo Produto. Faço isso através de um método adicionado no DTO que retorna um Produto em função dos valores dos atributos do DTO. Aqui tem um detalhe especial. Para eu conseguir construir um novo objeto do tipo Produto eu preciso construir um outro objeto do tipo Categoria representando a categoria do produto. Preciso sair do id da categoria mãe para uma Categoria de fato. Para fazer isso eu peço um EntityManager no método de conversão, para conseguir acessar o método findById. Se quisesse usar o princípio da segregação pela interface, eu poderia receber como argumento no método de conversão do DTO uma parâmetro do tipo Function e usar um method reference para passar a referência para o método find do EntityManager. Além disso eu também preciso associar o novo produto com seu dono. Então eu também peço um parâmetro do tipo Usuario no método de conversão.
- Crio a classe Produto. Um detalhe é que eu preciso receber a coleção de novas características no construtor. Eu vou decidir receber a coleção de NovaCaracteristicaRequest direto, acoplando essas camadas. O motivo dessa decisão é que decidi fazer com que a classe que CaracteristicaProduto tenha uma referência ao produto. E aí caí no dilema do ovo e da galinha… Como eu não tenho produto, como poderia criar a característica. Preciso de um jeito de receber os dados de cada característica e aí, a partir da criação do produto, criar a caractéristica em si. E não posso deixar de passar esses dados no construtor, já que o conjunto de características é obrigatório.
- Para fechar o código mencionado acima, eu adiciono o método de conversão lá na classe NovaCaracteristicaRequest, para sair dos dados da característica para uma Característica de fato. Neste método eu recebo como argumento um produto :).
- Faço o mapeamento na classe Produto para que o Hibernate consiga persistir os objetos. Importante aqui a utilização @OneToMany em cima do atributo do tipo Set<CaracteristicaProduto>. Aqui também é importante usar o parâmetro **mappedBy** no mapeamento da coleção, já que este mapeamento simplesmente é o mesmo do @ManyToOne que vai existir na classe CaracteristicaProduto.
- Adiciono as annotations de validação da bean validation na classe Produto.
- Crio a classe CaracteristicaProduto recebendo o nome, descricao e produto como argumento no construtor.
- Faço o mapeamento para que o hibernate consiga persistir objetos do tipo CaracteristicaProduto. Importante lembrar do ManyToOne em cima do atributo do tipo Produto.
- Adiciono as annotations de validação da bean validation na classe Produto.
- Recebo injetado o EntityManager no controller e gravo o produto no banco de dados.
- Deixo o Hibernate criar a tabela para mim no banco de dados. Poderia ter criado também o script e executado contra o banco

---

# **Usuário logado adiciona imagem no seu produto**

### **Explicação**

Com um produto cadastrado, um usuário logado pode adicionar imagens ao seu produto. Não precisa salvar a imagem em algum cloud ou no próprio sistema de arquivos. Cada arquivo de imagem pode virar um link fictício que pode ser adicionado ao produto.

### **Necessidades**

- Adicionar uma ou mais imagens a um determinado produto do próprio usuário

### **Restrições**

- Tem uma ou mais fotos
- Só pode adicionar fotos ao produto que pertence ao próprio usuário

### **Resultado esperado**

- Imagens adicionadas e 200 como retorno
- Caso dê erro de validação retorne 400 e o json dos erros
- Caso tente adicionar imagens a um produto que não é seu retorne 403.

1. Começaria pelo controller, anotado com @RestController, recebendo os dados via Post, com o corpo anotado com @Valid para validar os dados. O objeto de request em questão, será um DTO.
2. O DTO terá uma lista de MultipartFile e um Long productId.  O id do produto vem por meio da url, por exemplo: "produtos/10/imagens. e a imagem será automaticamente adicionada no DTO colocando a anotação @ModelAttribute ao receber o DTO no controller. Da mesma forma, o id do produto será automaticamente setado no DTO por meio do construtor.
3. Crio uma validação personalizada para verificar se o produto pertence ao usuário.
4. Crio uma anotação para verificar se o tipo do arquivo é imagem e anoto ela no MultipartFile no DTO. Também anoto o id do produto com exists, garantindo que existe.
5. Criaria a entidade para persistir com a JPA, portanto, anotaria a classe modelo com as anotações necessárias do JPA, além da bean validation. O construtor teria os atributos obrigatórios. A imagem terá um produto anotado com many to one e o produto terá uma coleção de imagens anotada com one to many com cascade do tipo merge assim que salvar as imagens, atualizar os produtos.
6. Crio um método no DTO para transferir pro Model. No caso, do DTO pra imagem, retorno uma lista de imagens.
7. Dentro do construtor da imagem, eu adiciono ela mesmo no produto recebido. Por exemplo: `product.addImage(this);`
8. Com a classe de modelo em mãos, basta persistir usando um entity manager que instanciamos no controller.
9. Para retornar 400, criarei um Controller advice, que irá tratar os erros de validações e retornar 400.

- Crio método novo no controller que adiciona produto
- O argumento do método do controller é do tipo de uma classe no estilo DTO para receber os dados das imagens que estão enviadas. Ali também eu uso a anotação @Valid para indicar a necessidade de validação. Eu não adiciono o @RequestBody, já que no upload o formato de envio é diferente.
- Crio a classe que representa os dados de entrada das imagens. Lá eu adiciono a annotation de validação @Size para garantir o tamanho mínimo. O atributo é do tipo List<MultipartFile>...
- No meu caso, como sugerido no projeto, eu ainda carrego o usuário com um email fixo para simular o usuário logado. Claro que também poderia configurar o Security e receber o usuário logado como argumento do método.
- Coloco como parte do endereço o id do produto que eu devo associar as imagens.
- Recebo injetado o EntityManager para carregar o produto pelo id.
- Carrego o produto pelo id.
- Verifico se o usuário logado é realmente dono daquele produto. Caso não seja, retorno o status que indica acesso negado.
- Agora preciso enviar as imagens para um lugar que saiba lidar com isso melhor que minha aplicação. Usaria um S3 da Amazon por exemplo. Mas ao mesmo tempo, por conta do desafio extra, preciso possibilitar que o upload aconteça de mentira em ambiente de desenvolvimento. Para isso faria o seguinte: criaria a interface Uploader com uma implementação inicial fake. A interface Uploader receberia a coleção de multipartfiles e retornaria uma coleção de String representando os links. Poderia ser uma coleção de URL também.
- Dada a implementação acima, eu também receberia injetado uma referência para um objeto cuja classe implementasse a interface Uploader. Aqui eu quebro a linha de controller 100% coeso, mas ainda mantenho a classe enxuta e com valores de métricas de complexidade baixos.
- Agora uso o objeto que faz upload e envio as imagens. Como resultado, estou com os links de destino das imagens no serviço externo.
- Agora invoco um método no objeto do tipo produto que associa estes links com o produto em si.
- Este método pega os links, converte para objetos do tipo ImagemProduto e guarda no atributo que representa a coleção de imagens. Poderia ser também uma coleção de Strings :).
- Esta classe ImagemProduto tem um link e um atributo do tipo Produto. Além do construtor recebendo as duas informações. Além disso tem um @ManyToOne como parte do mapeamento, ali no atributo do tipo Produto.
- Anoto a coleção de imagens com @OneToMany e uso o mappedBy para indicar que tal relacionamento já está mapeado por um @ManyToOne na outra classe.
- Ainda no atributo que representa a coleção de imagens eu uso o cascade do tipo MERGE, para forçar a atualização na chamada do merge no EntityManager. Aqui podem existir outros approaches. Ter a coleção de Strings era uma, assim como ter a classe ImagemProduto como Embeddable em vez de entity. Claramente a da String era a mais simpels, mas usariamos uma coleção de String para representar links para imagens. Decidi que era melhor aumentar a complexidade e criar uma classe de domínio específica.
- No controller, invoco o entityManager.merge em cima do produto, para forçar a atualização do produto. Aqui tem um acoplamento invisível :(. Só chamo o merge, porque o atributo com as imagens está anotado com cascade MERGE. Se tivesse usado String lá ou o Embeddable não precisaria disso. Por outro lado usando uma entidade, com uma complexidade similar a do Embeddable, eu já deixo a tabela do banco preparada para evolução. Ficar preparado para evolução sem aumentar a complexidade é ok.

---

# **Adicione uma opinião sobre um produto**

### **Explicação**

Um usuário logado pode opinar sobre um produto. O ideal era que isso só pudesse ser feito depois da compra, mas podemos trabalhar isso posteriormente..

### **Necessidades**

- Tem uma nota que vai de 1 a 5
- Tem um título. Ex: espetacular, horrível...
- Tem uma descrição
- O usuário logado que fez a pergunta (aqui pode usar usar o approach de definir um usuário na primeira linha do controller e depois trabalhar com o logado de verdade)
- O produto que para o qual a pergunta foi direcionada

### **Restrições**

- A restrição óbvia é que a nota é no mínimo 1 e no máximo 5
- Título é obrigatório
- Descrição é obrigatório e tem no máximo 500 caracteres
- Usuário é obrigatório
- O produto relacionado é obrigatório

1. Começaria pelo controller, anotado com @RestController, recebendo os dados via Post, com o corpo anotado com @Valid para validar os dados. O objeto de request em questão, será um DTO.
2. Anotaria as anotações necessárias do bean validation para que os campos fossem validados.
3. Receberia apenas o id do produto a ser avaliado, anotando com @Exists pra garantir que existe.
4. Recuperaria o user através do AuthenticationPrincipal
5. Criaria a entidade para persistir com a JPA, portanto, anotaria a classe modelo com as anotações necessárias do JPA, além da bean validation. O construtor teria os atributos obrigatórios. 
6. Na entidade, eu teria um produto associado com many to one e na entidade produto eu teria uma coleção de comentários anotada com one to many.
7. Para transformar de dto pra entidade, eu receberia o usuário o produto do controller. Eles serão usados na classe modelo `Opniao` . 
8. Com a opinião pronta, seto na coleção de produtos e dou um merge no produto
9. Para retornar 400, criarei um Controller advice, que irá tratar os erros de validações e retornar 400.

---

# **Faça uma pergunta**

### **Explicação**

Um usuário logado pode fazer uma pergunta sobre o produto

### **Necessidades**

- A pergunta tem utítulo
- Tem instante de criação
- O usuário que fez a pergunta
- O produto relacionado a pergunta
- O vendedor recebe um email com a pergunta nova
- O email não precisa ser de verdade. Pode ser apenas um print no console do servidor com o corpo.

### **Restrições**

- O título é obrigatório
- O produto é obrigatório
- O usuário é obrigatório

### **Resultado esperado**

- Uma nova pergunta é criada e é retornada. Status 200
- Em caso de erro de validação, retorne 400 e o json com erros.

1. Começaria pelo controller, anotado com @RestController, recebendo os dados via Post, com o corpo anotado com @Valid para validar os dados. O objeto de request em questão, será um DTO.
2. Anotaria as anotações necessárias do bean validation para que os campos fossem validados.
3. Criaria a entidade para persistir com a JPA, portanto, anotaria a classe modelo com as anotações necessárias do JPA, além da bean validation. O construtor teria os atributos obrigatórios. 
4. A entidade vai ter um usuário, que é quem fez a pergunta e um produto, que é o produto em questão. Ambos anotados com @Many to one.
5. Para transformar o DTO na entidade, peço o usuário autenticado e o produto nos parâmetros e seto na entidade via construtor obrigatório.
6. Para o envio de emails, crio uma interface de email com um método send que recebe uma pergunta como parâmetro. Agora crio um serviço de email falso que implementa essa interface.  Anoto o mesmo com @Component pra ser reconhecido como um bean do spring.
7. Com a classe de modelo em mãos, basta persistir usando um entity manager que instanciamos no controller e realizar o envio de email no controller mesmo.
8. Para retornar 400, criarei um Controller advice, que irá tratar os erros de validações e retornar 400.

---

# **Escreva o código necessário para montar a página de detalhe**

### **Explicação**

O front precisa montar essa página => [https://produto.mercadolivre.com.br/MLB-1279370191-bebedouro-bomba-eletrica-p-garrafo-galo-agua-recarregavel-_JM?quantity=1&variation=49037204722&onAttributesExp=true](https://produto.mercadolivre.com.br/MLB-1279370191-bebedouro-bomba-eletrica-p-garrafo-galo-agua-recarregavel-_JM?quantity=1&variation=49037204722&onAttributesExp=true)

Não temos todas as informações, mas já temos bastante coisa. Faça, do jeito que achar melhor o código necessário para que o endpoint retorne as informações para que o front monte a página.

### **Antes de começar**

Por favor descreva como você pretende realizar a implementação deste desafio.

**Para acessar o formulário [clique aqui](https://forms.gle/ePmQNnDwcqRpqv9b7)**

### **Informações que já temos como retornar**

- Links para imagens
- Nome do produto
- Preço do produto
- Características do produto
- Descrição do produto
- Média de notas do produto
- Número total de notas do produto
- Opiniões sobre o produto
- Perguntas para aquele produto

1. Como vou entregar os dados para cliente, eu anotaria o controller com @RestController para entregar os dados via json. 
2. Receberia o id do produto via Path Variable
3. Criaria os DTOs necessários:
    1. Para as características, poderia vir até ser um Map, mas creio que uma classe seja mais adequado, pois pode ser que possa vir comportamentos que terei que usar dentro dela, por isso, criaria um DTO a parte por hora.
    2. Para as opiniões, dado que uma opinião tem uma nota, um título e uma descrição, então criaria um dto aqui também.
    3. Agora basta criar o DTO central de saída, unir os outros 2 em coleções dentro desse DTO de produto e retornar para o cliente. 
    4. Para recuperar a média,. talvez eu usasse um sql com as funções de avarage, mas posso considerar realizar o calculo dentro de uma função específica que seria responsável por esses cálculos de opiniões em geral.
4. Retorno o objeto com response entity

---

# **Realmente finaliza compra | Parte 1**

### **Contexto**

Aqui a gente vai simular uma integração com um gateway como paypal, pagseguro etc. O fluxo geralmente é o seguinte:

- O sistema registra uma nova compra e gera um identificador de compra que pode ser passado como argumento para o gateway.
- O cliente efetua o pagamento no gateway
- O gateway invoca uma url do sistema passando o identificador de compra do próprio sistema e as informações relativas a transação em si.

Então essa é a parte 1 do processo de finalização de compra. Onde apenas geramos a compra no sistema. Não precisamos da noção de um carrinho compra. Apenas temos o usuário logado comprando um produto.

### **Necessidades**

- A pessoa pode escolher a quantidade de itens daquele produto que ela quer comprar
- O estoque do produto é abatido
- Um email é enviado para a pessoa que é dona(o) do produto informando que um usuário realmente disse que queria comprar seu produto.
- Uma compra é gerada informando o status INICIADA e com as seguintes informações:
    - Gateway escolhido para pagamento
    - Produto escolhido
    - Quantidade
    - Comprador(a)
    - Valor do produto naquele momento
- Suponha que o cliente pode escolher entre pagar com o Paypal ou Pagseguro.

### **Restrições**

- A quantidade é obrigatória
- A quantidade é positiva
- Precisa ter estoque para realizar a compra

### **Resultado esperado**

- Caso a pessoa escolha o paypal seu endpoint deve gerar o seguinte redirect(302):
    - Retorne o endereço da seguinte maneira: paypal.com?buyerId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}
- Caso a pessoa escolha o pagseguro o seu endpoint deve gerar o seguinte redirect(302):
    - Retorne o endereço da seguinte maneira: pagseguro.com?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}
- Caso aconteça alguma restrição retorne um status 400 informando os problemas.

1. Criaria um controller anotado com Rest controller, recebendo os dados via POST
2. Nos parâmetros solicito um DTO anotado com @Valid e o usuário logado.
3. No DTO vai ter o id do produto, a quantidade e o tipo de pagamento validados com as anotações do bean validation.
4. No controller, verifico se existe o produto, se não, lanço 404.
5. Com o produto em mãos, crio uma entidade Order que vai ter um produto e um usuário anotado com many to one e os atributos normais, tais como quantidade, preço, id e o tipo de pagamento.
6. Anoto a entidade com as anotações do JPA e as anotações do bean validation.
7. Crio um método que verifica se tem estoque  no produto e se tiver, reduz do estoque e retorna true, senao, retorna falso.
8. Se reduziu com sucesso, instancio um Order e passo os atributos necessários via construtor e persisto com entity managaer. Vale lembrar, que o product em Order é anotado com many to one e o cascade é do tipo merge, sendo assim, vai atualizar a quantidade nos produtos automaticamente.
9. Com serviço de email falso, envio o email para o dono do produto.
10. Retorno a url do produto com o método baseado no enum selecionado.

1. Crio o controller com as anotações necessárias. 
2. Recebo um DTO com @Valid via post.
3. Esse DTO, é referente a forma de pagamento, no nosso caso, pagseguro e paypal.
4. Esse mesmo dto que implementa uma interface com um método que transforma pra classe modelo recebendo um objeto Order.
5. Agora cada dto implementa e faz sua própria implementação pra retornar uma Transação.
6. No controller, eu procuro pelo id da Order que chegará pela url e eu recupero via path variable.
7. Verifico se essa ordem já foi concluída em um método interno, por exemplo: order.hasBeCompleted. Se tiver, retorno bad request dizendo que já foi concluída.
8. Senão, crio uma transação a partir do método interno da interface que criei, a toModel.
9. Finalizo a Order com um método que seta o status atual para finalizado, exemplo: order.finish() para que seja processada apenas uma vez.
10. Agora crio um objeto que recebe uma lista de "Tarefas a serem feitas" e que cada tarefa dessa implementa uma interface que tem um único método de processar.
11. Peço pro Spring instanciar eles pra mim e percorro a lista de tarefas passando a Order como parametro.
12. Envio os dados com rest template e envio os dados via map.
13. Crio um package para simular os sistemas externos e recebo os dados via dto de request com os dados necessários.