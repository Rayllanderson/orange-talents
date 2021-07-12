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