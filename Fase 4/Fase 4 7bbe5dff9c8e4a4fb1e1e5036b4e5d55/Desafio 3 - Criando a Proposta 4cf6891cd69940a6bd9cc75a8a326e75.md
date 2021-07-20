# Desafio 3 - Criando a Proposta

# 1. **Criação de uma nova proposta**

## **Objetivo**

Realizar a criação de uma proposta, durante o processo de criação da proposta deve ser checado restrições ao solicitante da proposta.

## **Necessidades**

- O documento necessário deve ser o CPF/CNPJ
- E-mail
- Nome
- Endereço
- Salário

## **Restrições**

- Documento do solicitante deve ser obrigatório e válido
- E-mail não pode ser vazio, nulo ou inválido
- Nome não pode ser vazio ou nulo
- Endereço não pode ser vazio ou nulo
- Salário bruto não pode ser vazio, nulo ou negativo

## **Resultado Esperado**

- A proposta deve estar armazenada no sistema, com um identificador gerado pelo sistema.
- Retornar **201** com Header Location preenchido com a URL da nova proposta em caso de sucesso.
- Retornar **400** quando violado alguma das restrições.

### Como eu faria...

1. Criaria o controller anotado com RestController para dizer ao Spring que estou trabalhando com uma api Rest e o nosso retorno será um corpo no formato JSON. Também mapeio esse controller com RequestMapping
2. Crio um DTO com os parâmetros necessários anotados com as anotações do bean validation. NotBlank, Email, Positive... E pro CPF e CNPJ, crio uma anotação personalizada que dentro dela eu uso uma constrain do tipo OR na anotação do hibernate validator CPF ou CPNJ.
3. No método anotado com @PostMapping, eu recebo o DTO anotado com Request Body e Valid para indicar ao Spring que vou receber um corpo da requisição e esse, por sua vez, será validado.
4. Crio a classe modelo anotada com as anotações do bean validation e do JPA para que minha classe modelo vire uma tabela. Também deixo ele gerar um id com auto increment.
5. Crio um Getter no id para ser usado na Location
6. Faço a transformação do DTO pra classe modelo com um método dentro do DTO que retorna a classe modelo. 
7. Salvo com entity manager.
8. Solicito um UriComponentsBuilder no método para buildar nossa uri de Location.
9. Retorno com Response entity created passando o uri criado com o id do objeto gerado.

---

# **Não pode haver mais de uma proposta para o mesmo solicitante**

## **Objetivo**

Criamos o fluxo de geração de proposta, porém nosso cliente solicitou uma alteração que consiste em adicionar uma nova validação. Entretanto, não podemos permitir a existência de mais de uma proposta para o mesmo solicitante, ou seja, para o mesmo CNPJ ou CPF.

## **Resultado Esperado**

Não podemos permitir que tenha mais de uma proposta para o mesmo solicitante, ou seja, para o mesmo CNPJ ou CPF.

- Devemos retornar o status code **422**, quando o solicitante já requisitou uma proposta.
- Permitir a criação de uma proposta, caso o solicitante não tenha nenhuma outra.

1. Crio uma anotação @Unique que recebe a entidade e o nome do atributo. 
2. No validador, verifico se já existe alguma outra proposta com o atributo recebido me baseando na entidade recebida. 
3. Se já existir, lanço uma exceção customizada, chamada: UnprocessableEntityException
4. Recebo a UnprocessableEntityException no meu expcetion handler e lanço 422.
5. Caso não exista, o fluxo seguirá normalmente. 

Opa, Alefh, desculpa incomodar, mas tô um pouco confuso sobre o desafio 3, ainda na parte do "Consultando dados do solicitante". 

Essa solicitação deve ser feita no mesmo endpoint de salvar a proposta? Neste fluxo:

- Salvamos a proposta,
- montamos a nova request com id da proposta no controller
- enviamos para o client com o Feign
- Recebemos a resposta e atualizamos a proposta com o status

Ou ela deve ser feita em um novo endpoint? Neste fluxo:

- Recebemos o body da solicitação de proposta
- Enviamos o mesmo para o Feign
- Recebemos o retorno
- Buscamos a proposta pelo id que veio no body da requisição
- Setamos o novo status e Atualizamos a proposta

Me parece que a solicitação deve ser feita quando a gente for salvar, mas isso não seria mais de uma responsabilidade para o método "salvar"?

Então o fluxo é o primeiro que você colocou, para salvar uma proposta precisamos definir o stauts dela, porém esse status a gente só consegue definir consultando o sistema de dados do solicitante e com base nessa resposta conseguimos definir se o staus será: **COM_RESTRICAO** ou **SEM_RESTRICAO**

Então no seu endpoint no momento que você receber os dados para criação da proposta também devera consultar esse sistema, caso você não consiga se comunicar com esse sistema deverá ser retornado um erro falando que no momento não é possível criar a proposta

como eu faria...

1. Ao salvar a proposta, eu consulto o sistema externo usando o RestTemplate. Para enviar os dados, eu crio um DTO de request e monto ele de acordo com a necessidade.
2. Recebo a resposta em um DTO de response e seto na proposta com um Enum.
3. Faço update da proposta

---

# **Melhorando a visibilidade da nossa aplicação para equipe de operação (Health Check)**

## **Objetivo**

Nossa aplicação deve "mostrar" a saúde dela para algum sistema automático de monitoramento ou equipe de operação!

## **Resultado Esperado**

Criação de um endpoint HTTP (REST) que "informe" a saúde da nossa aplicação.

- API deve retornar o status code 200 quando tudo estiver ok
- API deve retornar o status code 5xx quando algum componente de nossa infraestrutura estiver com mal-funcionamento (ex: banco de dados ou algum broker de mensagens)

1. Adiciono o Spring actuator na aplicação.
2. Configuro o endpoint de health no application.properties.
3. Acesso o status da api no endpoint actuator/health

# **Associar o cartão criado com a proposta**

## **Objetivo**

Atrelar o número do cartão gerado na proposta. A proposta quando aprovada deve ser relacionada com um número do cartão.

O sistema de proposta deve consultar em tempo periódico o sistema de cartões a fim de obter o número do cartão gerada para as propostas que foram geradas com sucesso, porém ainda não tem o número do cartão atrelado.

## **Necessidades**

Associar o número do cartão na proposta previamente criada com sucesso. O cartão deve ser preservado de acordo com as informações recebidas do sistema externo.

## **Restrições**

O identificador da proposta será utilizado como base para consulta do cartão no sistema legado "accounts".

Para consultar se o cartão foi criado com sucesso, temos uma API específica para este fim, vamos analisá-la?

`http://localhost:8888/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

## **Resultado Esperado**

- Quando o sistema de accounts(cartões) retornar sucesso (status code na faixa 200) o número de cartão deve ser atrelado a proposta.
    - O **número do cartão** é o **id** do cartão retornado na resposta do sistema de accounts(cartões)
- Quando o sistema de accounts(cartões) retornar falha (status code na faixa 400 ou 500) não atualizar o estado da proposta, pois ainda não foi processado, aguardar próxima interação.

1. Crio um Feign para lidar com a comunicação com o outro serviço, configurando url no properties.
2. Crio um DTO de request para ser enviado para a outro servico.
3. Crio um DTO de response que será a resposta do outro serviço.
4. Para que seja consultado periodicamente, eu crio um método anotado com @Scheduled, configurando o fixed delay no properties. Dessa forma, tudo dentro desse método terá um certo delay após a execução.
5. Crio um repositório de propostas para trazer as propostas elegíveis e sem cartão.
6. Faço um for nessa lista (caso haja algum), ele vai enviar a requisição para o outro service, trazer os resultados e com isso, posso atrelar o cartão gerado com a proposta. Agora salvo. Importante dizer que eu usarei o Executor de transações aqui ao invés do repositório. Não tenho certeza, mas me parece que o repositório deixaria a transação aberta por um bom tempo. Por isso, prefiro usar o executor de transações aqui. 
7. Caso ocorra algum erro, recupero o erro e apenas "logo" na aplicação.

---

# **Acompanhamento da Proposta**

## **Objetivo**

Criação de um endpoint que informe os dados da proposta.

## **Necessidades**

O solicitante pode consultar o estado da sua proposta.

## **Restrições**

O identificador da proposta é obrigatório na URL.

## **Resultado Esperado**

- Retornar status code **200** com a proposta no corpo da resposta.
- Retornar status code **404** quando a proposta não existir.

1. Crio um endpoint que recebe o id na url usando o path variable. Recebo por GET aqui, visto que é apenas pra consulta.
2. Peço para que o spring instancie um repositório de proposta. Dou um find by id passando o id, se não encontrar, throw ResponseStatusException passando o status not found.
3. Crio um DTO de response que terá o status da proposta atual.
4. Passo o status para o DTO via construtor, ele transforma lá dentro em String e retorno o DTO de response.

---

# **Vamos rodar nossa aplicação**

## **Objetivo**

Nossa aplicação está apta a ser executada em algum ambiente, mas qual a maneira mais adequada para rodar essa aplicação. Instalar um artefato em algum servidor de aplicação ou webserver. Pode não ser uma boa saída!

Quando pensamos em um ambiente distribuído, nossas aplicações precisam ser **auto-contidas**, ou seja elas precisam expor seus serviços via HTTP, ou porta de um serviço web. Dessa maneira conseguimos escalar nossa aplicação usando o modelo "escalabilidade horizontal" ou adicionando novas instâncias desses serviços.

## **Explicação Necessária**

Em um ambiente de computação distribuída, aplicações "nascem" a todo instante e preparar um servidor web para depois realizar a instalação consome muito tempo, ainda temos um agravante da generalização correta dos serviços, fazendo com que uma aplicação seja executada com uma "carga" não tão balanceada.

Outra característica de uma aplicação distribuída é reagir de uma maneira eficiente com aumento de carga e conseguir "ficar pronta" de maneira rápida, adicionando novas instâncias ao pool de instâncias que atendem as requisições.

Esses itens referem-se a dois tópicos do manifesto 12 factor Apps, que garante que nossa aplicação seja portátil e rode eficientemente em ambientes cloud. Item VI, VII e VIII do [manifesto](https://12factor.net/pt_br/)

## **Necessidades**

Precisamos rodar nossa aplicação fazendo exposição da porta para acesso ao serviço criado utilizando **Docker**

## **Resultado Esperado**

- Conseguir realizar chamada no serviço criado via porta HTTP utilizando **Docker**

1. Crio um dockerfile e configuro minha aplicação.
2. Dou um `mvn clean package` para criar o target da aplicação e conferir se está tudo ok.
3. Configuro o build da minha aplicação no docker-compose.
4. Crio uma network no arquivo do docker-compose. 
5. Uso essa network no banco de dados em minhas aplicações necessárias.
6. Configuro porta e outras variáveis de ambientes necessárias da nossa aplicação, como banco de dados e afins.
7. subo com docker-compose up e realizo uma requisição na porta configurada no docker-compose pra ver se está funcionando 

---

# **Criar biometria**

## **Objetivo**

O portador do cartão deseja realizar o cadastro da biometria para conseguir acesso ao aplicativo usando a mesma. O cartão pode ter uma ou mais biometrias associadas.

## **Necessidades**

Realizar o cadastro da biometria. Devemos armazenar a data em que a biometria foi associada para futuras auditorias.

- Informar o identificador do cartão.
- Informar um fingerprint da biometria.

## **Restrições**

- Identificador do cartão é obrigatório na URL (path parameter).
- Biometria deve ser enviada em Base64.

## **Resultado Esperado**

- A biometria deve estar armazenada no sistema, com um identificador gerado pelo sistema.
- Retornar **201** com Header Location preenchido com a URL da nova biometria em caso de sucesso.
- Retornar **400** quando a biometria não é enviada ou está inválida.
- Retornar **404** quando o cartão não for encontrado.

1. Crio um controller anotado com Rest controller.
2. Crio um método que recebe o id do cartão via path variable. O mesmo anotado com post mapping.
3. Recebo um DTO como corpo da requisição anotado com valid
4. Crio uma entidade para representar a tabela no banco, anotada com as anotações do jpa e deixo que o jpa crie o id pra mim. Na entidade, a relação de biometria com cartão será de ManyToOne. Também crio um Set de biometrias na entidade Cartão anotada com one to many.
5. O DTO de request, terá uma String da impressão digital, validado com not blank e também crio uma validação personalizada para base 64. Dessa forma, retorna 400 se estiver inválido.
6. No método do controller, procuro o produto via entity manager, se não encontrar, retorno um response entity not found.
7. Se encontrar, passo o Cartão pro método toModel do response, que vai instanciar uma nova biometria passando o cartão em questão.  Persisto e gero a uri com UriComponentsBuilder. 
8. Retorno response entity created passando a uri criada.

---

# **Login via senha cartão**

## **Contexto**

Controlar autenticação e autorização é uma tarefa complicada e que na maioria das vezes envolve um conhecimento profundo em segurança, como por exemplo se preocupar com vulnerabilidades de segurança, trazer essa responsabilidade para nossa solução pode trazer muitas complicações.

Vamos deixar essas características para uma outra aplicação, a IAM (Identity and Access Management).

## **Objetivo**

Realizar a integração do nosso sistema com o Keycloak, a fim de proteger nossas APIs.

## **Necessidades**

Precisamos configurar nosso sistema para se comunicar com nosso servidor de autenticação.

## **Restrições**

- Não vamos realizar a manipulação de usuários, então não podemos criar nenhum usuário no sistema.
- Antes de começarmos a configuração na nossa aplicação vamos precisar realizar algumas tarefas
    - Logar no Keycloak nosso Servidor de IAM, [aqui tem um passo-a-passo de como fazer isso](https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/informacao_suporte/keycloak-login.md)
    - Nosso próximo passo será criar um "espaço" para colocar nossas permissões, no keycloak esse "espaço" chama-se Realm, [aqui você encontra o que você precisa para criar um realm](https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/informacao_suporte/keycloak-realm.md)
        - Criar usuários para realizar logins na plataforma, [aqui você pode encontrar como fazer isso](https://github.com/zup-academy/nosso-cartao-documentacao/blob/master/informacao_suporte/keycloak-user.md)

## **Resultado Esperado**

Configuração do Spring Security na nossa aplicação com o módulo OAuth2 apontando para o nosso servidor de Autorização, nesse caso o Keycloak.

1. Configuro o Klayclock, crio o nosso Real, client, users, scopes e afins.
2. Adiciono as dependências do OAuth2 no pom.xml
3. Configuro no [application.properties](http://application.properties) a url do nosso authorization server, o kaycloak. E também do outra url pra validar a assinatura do token.
4. Configuro os endpoints no Spring security pra cada escopo.
5. Adiciono oauth2ResourceServer também nas configurações do Spring security

Opa, alefh, tudo bom? Então, estou fazendo o login com OAuth2, mas sempre recebo 401 dizendo que não estou autenticado. Chequei os headers de response, e lá dizia: Bearer error="invalid_token", error_description="An error occurred while attempting to decode the Jwt: Jwt expired at 2021-07-14T08:32:00Z"

Diz que meu JWT está expirado. Achei estranho, visto que gerei agora mesmo... Fui conferir algo no Keycloak e vi que o servidor está com a data de ontem. Procurei e não encontrei nada relacionado. Então executei a query no postgres pra ver a hora e realmente consta como dia 14. 

Ou seja, estou supondo com quase toda certeza que o problema é o docker. Tem como configurar a data e hora no docker-compose pra resolver esse problema?

---

# **Bloqueio do Cartão**

## **Objetivo**

Realizar o bloqueio do cartão.

## **Necessidades**

O usuário do cartão pode realizar o bloqueio do cartão por alguma suspeita de fraude.

- Informar o identificador do cartão a ser bloqueado.
- Armazenar o instante do bloqueio.
- Armazenar o IP do cliente que fez a requisição.
- Armazenar o User Agent do cliente que fez a requisição.

## **Restrições**

- O identificador do cartão é obrigatório e deve ser informado na URL (path parameter).
- Caso o cartão esteja já bloqueado devemos retornar um erro de negócio.

## **Resultado Esperado**

- Bloqueio deve estar armazenada no sistema, com um identificador gerado pelo sistema.
- Retornar **200** quando o bloqueio em caso de sucesso.
- Retornar **400** quando violado alguma das restrições.
- Retornar **422** quando violado alguma regra de negócio.
- Retornar **404** quando o cartão não for encontrado.

 

1. Crio um controller anotado com rest controller, mapeado com /cards
2. Recebo o id do cartão via path variable. 
3. Utilizo post pra requisição
4. Procuro o cartão pelo id, se não encontrar, retorno NOT_FOUND
5. no cartão, crio um enum que representa o estado do cartão. Se tá bloqueado ou não.
6. No controller, vejo se o cartao está bloqueado: cartao.estaBloqueado(). se sim, retorno UNPROCESSABLE_ENTITY. Esse método verifica se o estado do cartão é bloqueado.
7. No cartão, crio um método pra bloquear o cartão, recebendo o ip e o user agent. 
8. Valido os campos, valido se está bloqueado e instancio uma nova entidade que representa o bloqueio, passando ip, user agent e o próprio cartão.
9. Crio essa entidade anotada com @Entity, anoto o cartão com Many to One, e com os restante dos atributos devidamente anotados. (bean validation e outras do jpa)
10. Depois de associar o cartão com a entidade de bloqueio, crio uma lista de bloqueios dentro do cartão e, no mesmo método de bloqueio, adiciono o novo bloqueio à lista. Essa lista terá o cascade CascadeType.MERGE
11. No controller, depois de acabar o método block, eu dou um merge no cartão e pronto, vai salvar o atual status e a entidade de bloquei também.
12. Retorno response entity.ok()

---

# **Notificando o sistema legado do bloqueio do nosso cartão**

## **Objetivo**

Precisamos notificar o sistema legado do banco, que houve um bloqueio no cartão. Para que de fato o cartão esteja bloqueado em todos os canais de venda.

## **Necessidades**

Notificar o sistema bancário que o usuário solicitou o bloqueio do cartão a fim de garantir o bloqueio em todos os canais de utilização do mesmo.

Temos uma API específica para notificar o sistema bancário sobre o bloqueio do cartão, vamos analisá-la?

`http://localhost:8888/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

## **Restrições**

O identificador do cartão é obrigatório.

## **Resultado Esperado**

- Quando o retorno do sistema bancário retornar sucesso (status code na faixa 200) devemos alterar o estado do cartão para "BLOQUEADO".
- Quando o retorno do sistema bancário retornar erro (status code na faixa 400 ou 500) não devemos alterar o estado do cartão.

1. Utilizo o feign pra comunicar com o sistema externo dentro dessa classe.
2. Caso lance alguma exception, lanço outra para o handler capturar e mostrar a mensagem.
3. Caso não, continuo com a aplicação normalmente. Sigo o fluxo e salvo.

---

# **Como saber se o nosso sistema está funcionando corretamente**

## **Objetivo**

Precisamos encontrar uma maneira eficiente de entender se o nosso sistema está funcionando da maneira "adequada".

Imagine num ambiente com múltiplas instâncias do mesmo tipo de serviço, por exemplo 15 instâncias do serviço de TRANSACTIONS, conectar a cada instância e verificar o log de cada uma, pode não ser uma boa ideia.

Armazenar métricas certamente não é uma responsabilidade do nosso serviço. Como podemos fazer isso?

## **Descrição**

Métricas são muito importantes num ambiente de sistemas distribuídos, expor para um sistema de coleta externa facilita a utilização da métrica de maneira mais efetiva, afinal não podemos armazenar métricas no serviço. Armazenar traria uma complexidade em lidar com dados no tempo "time series database", além de não fazer parte do nosso contexto de sistema.

As métricas a serem expostas devem ser exclusivamente da única instância em questão, não precisamos saber de outras, afinal temos um sistema externo que lida com isso, só precisamos adicionar um identificador do nosso sistema, que na verdade é o provedor da métrica.

Um bom ponto de partida em qual métrica exportar pode ser encontrado em: [https://www.weave.works/blog/the-red-method-key-metrics-for-microservices-architecture/](https://www.weave.works/blog/the-red-method-key-metrics-for-microservices-architecture/)

Precisamos aumentar a "visibilidade" do comportamento do sistema, assim a equipe de operação \ sustentação pode identificar qualquer anomalia nas nossas aplicações.

## **Necessidades**

Precisamos criar um endpoint HTTP REST para "mostrar" a métrica da instância para um serviço externo de armazenamento de métrica. Vamos usar o formato do Prometheus que é o padrão da Cloud Native Computing Foundation.

## **Resultado Esperado**

Endpoint com métricas expostas para uma futura coleta.

1. Adiciono o Micrometer ao nosso pom.xml
2. Libero o endpoint do prometheus no application.properties
3. Configuro o prometheus com prometheus.yml
4. Configuro a imagem dele no docker pra encontrar o nosso yml.
5. Subo a imagem, subo a aplicação e tenho acesso as configurações acessando o prometheus na porta configurada.

---

# **Aviso de Viagem**

## **Objetivo**

Cadastrar um aviso de viagem para o cartão.

## **Necessidades**

O portador do cartão pode realizar uma notificação para o banco, dizendo que pretende utilizar o cartão em um determinado destino, isso ajuda o banco no controle das fraudes.

- Informar o identificador do cartão.
- Informar o destino da viagem.
- Informar a data do término da viagem.
- Armazenar o instante do aviso de viagem.
- Armazenar o IP do cliente que fez a requisição.
- Armazenar o User Agent do cliente que fez a requisição.

## **Restrições**

- O identificador do cartão é obrigatório e deve ser informado na URL (path parameter).
- O destino da viagem é obrigatório, ou seja, não pode ser nulo ou vazio.
- A data do término da viagem é obrigatória, ou seja, não pode ser nulo ou uma data antiga.

## **Resultado Esperado**

- O aviso de viagem deve estar armazenado no sistema, com um identificador gerado pelo sistema.
- Retornar **200** em caso de sucesso.
- Retornar **400** quando violado alguma das restrições.
- Retornar **404** quando o cartão não for encontrado.

1. Crio um controller anotado com rest controller, mapeado com /cards
2. Recebo o id do cartão via path variable. 
3. Utilizo post pra requisição
4. Procuro o cartão pelo id, se não encontrar, retorno NOT_FOUND
5. Para capturar o ip e o user agent, peço pra que o Spring instancie um http servlet request no parâmetro do método.
6. Crio um DTO pra receber os dados da requisição.
7. Dentro do dto, crio um método pra transformar para o model, passando o cartão
8. Crio uma entidade de modelo contendo os atributos necessários, mapeado com as anotações do jpa necessárias. Também adiciono o cartão e anoto com many to one.
9. No método to model do dto, instancio a entidade passando os atributos via construtor.
10. No controller, salvo via entity manager.

---

# **Notificação do sistema bancário de aviso de viagem**

## **Objetivo**

O sistema bancário precisa ser notificado que foi realizada uma notificação de aviso de viagem.

## **Necessidades**

Realizar a confirmação da notificação do aviso de viagem para o sistema bancário. A chamada deve ser realizada para o sistema de accounts (cards).

Temos uma API específica para notificar o sistema bancário sobre o aviso de viagem, vamos analisá-la?

`http://localhost:8888/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

## **Restrições**

- O identificador do cartão é obrigatório e deve ser informado na URL (path parameter).
- O destino da viagem é obrigatório, ou seja, não pode ser nulo ou vazio.
- A data de validade da viagem é obrigatória, ou seja, não pode ser nulo ou uma data antiga.

## **Resultado Esperado**

- Quando o sistema bancário retornar sucesso (status code na faixa 200) o aviso deve ser armazenado no sistema.
- Quando o sistema bancário retornar erro (status code na faixa 400 ou 500) o aviso de viagem não deve ser armazenado no sistema.

1. Utilizo o feign pra comunicar com o sistema externo.
2. Caso lance alguma exception, lanço outra para o handler capturar e mostrar a mensagem.
3. Caso não, continuo com a aplicação normalmente. Sigo o fluxo e salvo.

---

# **Associação do cartão com Paypal**

## **Objetivo**

Realizar a inclusão do nosso cartão na carteira digital Paypal.

## **Necessidades**

O portador do cartão deseja associar seu cartão à carteira digital do Paypal, para isso será necessário consumir a API do sistema bancário.

Temos uma API específica para cadastrar a carteira digital, vamos analisá-la?

`http://localhost:8888/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

## **Restrições**

Devemos criar uma API com as seguintes restrições:

- O identificador do cartão é obrigatório e deve ser informado na URL (path parameter).
- O email é obrigatório, ou seja, não pode ser nulo, vazio ou inválido.
- O mesmo cartão não pode ser associado mais de uma vez a mesma carteira digital, no caso aqui o Paypal.

## **Resultado Esperado**

- A carteira deve estar armazenada no sistema, com um identificador gerado pelo sistema.
- Retornar **201** com Header Location preenchido com a URL da carteira em caso de sucesso.
    - Quando o sistema bancário retornar sucesso (status code na faixa 200) a carteira deve ser armazenada no sistema.
    - Quando o sistema bancário retornar erro (status code na faixa 400 ou 500) a carteira não deve ser armazenada no sistema.
- Retornar **400** quando violado alguma das restrições.
- Retornar **404** quando o cartão não for encontrado.
- Retornar **422** no caso de tentativa de associação de um cartão que já era associado com a carteira

1. Crio um controller e um método que recebe o id do cartão. Também recebo um body anotado com request body e valid. Nele vai conter apenas o email, anotado com as anotações do bean validation.
2. No método, verifico se o card existe, se não existir, retorno 404.
3. Crio o model do paypal, com um card dentro anotado com one to one.
4. Instancio o model do paypal no controller, passando o card e o email da requisição.
5. crio um método no cartão pra associar o paypal com cartão. Retorna um boolean, true caso ocorra tudo ok, false caso já esteja associado.
6. Caso dê false, retorno 422.
7. Tento me comunicar com sistema externo usando feign. Se houver alguma exception, lanço uma exceção que será tratada pelo rest controller advice.
8. caso passe tudo ok, eu persisto o paypal. No mapeado da entidade com cartão está cascade type merge, por isso salvo direto o paypal aqui.
9. utilizo o uri components builder pra buildar a uri de retorno. Retorno response entity created passando essa uri.

---

# **Associação do cartão com Samsung Pay**

## **Objetivo**

Realizar a inclusão do nosso cartão na carteira digital Samsung Pay.

## **Necessidades**

O portador do cartão deseja associar seu cartão a carteira digital do Samsung Pay, para isso será necessário consumir a API do sistema bancário.

Temos uma API específica para cadastrar a carteira digital, vamos analisá-la?

`http://localhost:8888/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/`

## **Restrições**

Devemos criar uma API com as seguintes restrições:

- O identificador do cartão é obrigatório e deve ser informado na URL (path parameter).
- O email é obrigatório, ou seja, não pode ser nulo, vazio ou inválido.

## **Resultado Esperado**

- A carteira deve estar armazenada no sistema, com um identificador gerado pelo sistema.
- Retornar **201** com Header Location preenchido com a URL da carteira em caso de sucesso.
    - Quando o sistema bancário retornar sucesso (status code na faixa 200) a carteira deve ser armazenada no sistema.
    - Quando o sistema bancário retornar erro (status code na faixa 400 ou 500) a carteira não deve ser armazenada no sistema.
- Retornar **400** quando violado alguma das restrições.
- Retornar **404** quando o cartão não for encontrado.

1. Deleto a classe paypal, crio uma genérica, algo como: CarteiraDigital. Nela, vai conter os mesmos atributos da classe papayl. Ressalva aqui que vou mudar o mapeamento do cartão. De one to one, vai pra many to one, visto que agora um cartão pode ter uma ou mais carteiras digitais. 
2. Adiciono um enum na entidade pra simbolizar os nomes das carteiras digitais. Samsung pay e paypal, cada uma com o valor do endpoint passado via construtor, que será usado pra deixar nosso controller mais genérico.
3. No controller, transformo o método de salvar paypal em um genérico, me baseando no nome do método de pagamento. Mas ainda deixo os métodos de endpoints separados, apenas crio um método que vai processar.
4. Se tudo correr bem, devolvo um header baseado no valor do enum que passei via construtor. exemplo: CarteiraDigitalNome.getValorEndpoint(); Na qual CarteiraDigitalNome seria um nome constante, como: SAMSUNG_PAY e o valor do endpoint seria: samsung-pay. 

---

# **As coisas podem falhar durante as chamadas. Como posso identificar isso?**

## **Objetivo**

Nossos serviços precisam se comunicar com outros serviços, isso é bastante presente nos modelos de arquiteturas atuais. Essas comunicações podem ser simples, um serviço chamando outro, até mais complexos como por exemplo um serviço chamando 5 serviços diferentes. Como podemos saber identificar unicamente uma requisição e entender o comportamento dessas requisições durante o ciclo de vida dela?

## **Descrição**

Trace distribuído é uma maneira bem simples de se entender, mas também muito efetiva de nos ajudar na tarefa de "rastrear" os passos de uma requisição dentro da nossa infraestrutura.

A ideia consiste na criação de um ou mais identificadores externos, e repassá-lo nas chamadas entre todos os sistemas envolvidos. Essa técnica também ficou bastante conhecida como Correlation Id. Quando utilizamos serviços HTTP esse "repasse" se dá pelos HTTP Headers.

Um padrão amplamente utilizado pela comunidade e o OpenTracing ou OpenTelemetry que é mantido pela [Cloud Native Computing Foundation](https://www.cncf.io/). Implementações como [Jaeger](https://www.jaegertracing.io/) ou [Zipkin](https://zipkin.io/) são suportadas.

## **Necessidades**

Repassar headers requeridos pelo padrão OpenTracing para todas as interações entre serviços, via HTTP Headers.

## **Resultado Esperado**

Informações sobre tracing presente em todas as requisições.

1. Adiciono a dependência no pom
2. Adiciono as configurações necessárias no application.properties
3. Pronto! O Jeager já vai ficar de olho nos nossos endpoints.

---

# **Armazenando o documento do nosso cliente de modo seguro!**

## **Objetivo**

Proteger os dados sensíveis dos nossos clientes.

## **Necessidades**

Armazenar o documento do cliente de maneira criptografada.

## **Restrições**

- Não deve ser possível identificar o documento do cliente na base de dados.

## **Resultado Esperado**

- O documento deve ser armazenado criptografado no banco de dados.

1. Crio uma classe que configura um TextEncryptor, passando o Salt e o password.
2. Crio um método estático pra criptografar e outro pra descriptografar. 
3. No construtor da proposta, criptografo o documento. E ao dar get no documento, uso o método de  descriptografar o mesmo. Assim, salvará criptografado, mas na nossa aplicação, trabalharemos com ele descriptografado.
