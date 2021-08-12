# Desafio 4 - Transações

# **Transações do Cartão**

## **Objetivo**

Não temos controle das transações de um usuário. Mas devemos fornecer uma experiência de consulta de transações para o usuário final.

As transações são realizadas por um conjunto de sistemas legados, esses sistemas não possuem APIs REST modeladas, necessidade proveniente da equipe de mobile, os sistemas também não suportam o volume de operações de consultas esperado.

## **Descrição**

É bastante comum que sistemas legados, não sejam implementados usando padrões de comunicação dos dias atuais, afinal eles foram construídos a muito anos atrás.

A equipe de desenvolvimento dos sistemas legados nos avisou que suportar APIs REST não é uma opção viável no momento, devido às restrições tecnológicas, porém, eles conseguiriam enviar todas as transações em um tópico do Kafka. Atuando como um "streaming" de pagamentos.

A equipe de desenvolvimento está enviando as transações no tópico do Kafka denominado `transacoes` com o seguinte formato:

```json
{
   "id":"c63fd0e0-eccb-4af3-9d49-39cde0ffdaf1",
   "valor":1.4874248222626738,
   "estabelecimento":{
      "nome":"B. A. Ware",
      "cidade":"North Winstonbury",
      "endereco":"18327 Mills Groves, West Marquita, SD 31244"
   },
   "cartao":{
      "id":"b0012b90-42c8-40e6-903b-64acb3aa649b",
      "email":"luram.archanjo@zup.com.br"
   },
   "efetivadaEm":"2020-08-10T13:16:56"
}
```

Para estimular a geração das transações é preciso efetuar uma requisição para o sistema bancário, conforme exemplo abaixo:

**Requisição**

`curl --location --request POST 'http://localhost:7777/api/cartoes' \
--header 'Content-Type: application/json' \
--data-raw '{
  "id": "<NÚMERO DO CARTÃO>",
  "email": "<EMAIL DO USUÁRIO LOGADO>"
}'`

**Exemplo**

`curl --location --request POST 'http://localhost:7777/api/cartoes' \
--header 'Content-Type: application/json' \
--data-raw '{
  "id": "5541da9c-79c5-44fb-b701-cc50ed07b45d",
  "email": "luram.archanjo@zup.com.br"
}'`

A partir do momento do estímulo, será gerado uma transação a cada 5 minutos, caso deseja parar a geração de transação, devemos remover o mesmo do sistema bancário, conforme exemplo abaixo:

**Requisição**

`curl --location --request DELETE 'http://localhost:7777/api/cartoes/<NÚMERO DO CARTÃO>'`

**Exemplo**

`curl --location --request DELETE 'http://localhost:7777/api/cartoes/5541da9c-79c5-44fb-b701-cc50ed07b45d'`

## **Necessidades**

Implementar o consumo das mensagens do tópico de transações.

## **Resultado Esperado**

Novo serviço recebendo informações de transações de cartões.

1. Coloco a dependência do Kafka no pom.xml, 
2. Configuro as propriedades do Kafka no application.properties, tais como deserializador de chave e valor, group-id e etc.
3. Crio as classes de dtos pra consumir as informações de resposta que vem do Kafka.
4. Crio uma classe de configuração do Kafka pra dizer que vou receber objetos do tipo "TransacaoDTO" por exemplo.
5. Crio um listener anotado com @KafkaListener que vai ficar ouvindo alguma transacaoDTO chegar.

---

# **Consultar compras recentes**

## **Objetivo**

Buscar as últimas transações do cartão de crédito.

## **Necessidades**

O portador do cartão deseja realizar uma consulta para obter as últimas compras do cartão de crédito.

## **Restrições**

Devemos criar uma API com as seguintes restrições:

- O identificador do cartão é obrigatório e deve ser informado na URL (path parameter).

## **Resultado Esperado**

- Retornar status code **200** com as últimas 10 compras (transações)
- Retornar status code **404** quando o cartão não for encontrado.

1. Pra buscar as informações, eu tenho que persistir, então adiciono banco de dados e configurações do banco no properties.
2. Crio a classe modelo da transação e crio um método que converte o dto de mensagem do kafka em model. Tipo: transacaoDtoMensagem.toModel();
3. No listener, adiciono um repository ou entity manager que salva a transação convertida em model.
4. Crio um controller, instancio um repository de transação. 
5. Crio um método que recebe um Page no repository que busca as transações baseadas no id do cartão.
6. No controller, crio um método anotado com GetMapping que recebe o id do cartão via path variable. 
7. Pra verificar se o cartao existe, posso dar um select count com a ajuda do repository, ou um exists by cartao id, ou mesmo verificar se a página retornada está vazia. Caso não exista, lanço um response status exception, passando o 404.
8. Crio um DTO de saída que tem um método que converte a page de transações em uma page de transações reponse.
9. retorno com response entity ok passando o dto.

---

# **Autenticação e Autorização**

## **Contexto**

Construir APIs é uma tarefa bem divertida, porém as APIs quando mal implementadas expõem dados sensíveis do negócio em questão, permite que haja vazamento de dados dos nossos clientes e outras coisas que causam uma reação bastante ruim para o negócio em si.

Implementar mecanismos de segurança em nossas APIs são primordiais e devem ser pensados desde o primeiro dia do projeto, devemos incorporar segurança em nosso design de código e arquitetura.

## **Objetivo**

Proteger nossa aplicação de ataques externos, vazamentos de dados e ataques nocivos ao nosso negócio.

## **Necessidades**

Configurar mecanismo de segurança na aplicação de fatura. Vamos adotar o padrão de mercado chamado OAuth2.

## **Restrições**

- Não vamos realizar a manipulação de usuários, então não podemos criar nenhum usuário no sistema.
- Utilizar a claim JWT **scope** para proteger a aplicação.

## **Resultado Esperado**

- Nossa aplicação de **transação** deve atuar como entidade **Resource Server**!
- Configuração do Spring Security na nossa aplicação com o módulo OAuth2 apontando para o nosso servidor de Autorização, nesse caso o Keycloak!

1. Adiciono as dependências necessárias.
2. Subo o keycloack via docker.
3. Configuro o keycloack, criando real, aplicação, scopes e users.
4. Seto a issuer uri e o validador do jwt no application properties.
5. Crio uma classe de configuração do spring security pra configurar a nossa aplicação pra ser um resource server. Também pra bloquear/liberar os endpoints de acordo com scope.