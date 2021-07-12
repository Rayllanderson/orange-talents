# Segurança de aplicações cloud-native

Aplicações cloud-native estão aptas para rodar em qualquer lugar, o que significa que não sabemos aonde nossa aplicação de fato vai rodar. Por esse motivo, devemos nos preocupar ainda mais em relação a segurança.

Provedores de nuvem pública possuem um elevado nível de segurança, mas pouco adianta se nossas aplicações não implementarem e seguirem boas práticas de segurança.

O processo de adição de segurança deve ser iniciado no momento da criação do projeto e continuamente sendo trabalhado junto com cada nova feature. Ou seja, devemos nos preocupar igualmente com a segurança a medida que vamos implementando novas features. Meio que mudar nosso pensamento de: *tenho que implementar isso...* PARA*: tenho que implementar isso e, pra isso, preciso fazer isso aq na segurança...* 

## Algumas técnicas de segurança:

### 1 - O que é ofuscamento? Quando devo usá-lo?

O ofuscamento é uma prática que "embaralha" os caracteres para proteger nossa informação de maneira que se a informação for analisada por qualquer fonte não seja possível identificar o dado.

Em alguns casos precisamos "logar" algumas informações que possam identificar uma pessoa e nesse caso que começam nossos problemas.

| Informação| Aberta | Ofuscada  |
| ------------- |:-------------:| -----:|
|Cartão Crédito | 4716750056121368 | ***********1368 |
| CPF      | 635.247.373-31|   635..-31 |
| Email | [joe@doe.com](mailto:joe@doe.com)      |    j**@***.com|

Perceba que com essas informações não é possível realizar a identificação da pessoa pelo documento ou email, o mesmo vale para o cartão de crédito não é possível utilizá-lo em qualquer compra.

> **Mas qual lugar ou camada eu preciso ofuscar dados?**

Você deve ofuscar dados sensíveis sempre, sempre que houver um log com dado sensível. Os lugares mais comuns são:

- Logs de aplicação
- Logs de APIs no API Manager

### 2 - Quando devo encriptar dados em trânsito? Qual é a motivação?

Nossas aplicações nunca vivem sozinhas, sempre precisamos realizar chamadas para outras aplicações para concluir uma determinada tarefa ou para persistir nossas informações no banco de dados.

Essas aplicações se comunicam via rede, ou seja, há uma transmissão de dados entre as aplicações.

Algum agente mal-intencionado consegue interceptar a comunicação e "roubar" os dados que estão passando por esse canal. Como no exemplo:

![Seguranc%CC%A7a%20de%20aplicac%CC%A7o%CC%83es%20cloud-native%205eebd9add709497ca9e4442074ec8a79/Untitled.png](Seguranc%CC%A7a%20de%20aplicac%CC%A7o%CC%83es%20cloud-native%205eebd9add709497ca9e4442074ec8a79/Untitled.png)

Para nos proteger desses ataques sempre precisamos usar um canal de comunicação seguro, um modelo que nos permita nos autenticar e realizar a encriptação da mensagem antes do envio.

Neste caso, quando o atacante obter acesso às informações essas informações vão estar criptografadas de maneira que a informação não tenha serventia ao atacante.

![Seguranc%CC%A7a%20de%20aplicac%CC%A7o%CC%83es%20cloud-native%205eebd9add709497ca9e4442074ec8a79/Untitled%201.png](Seguranc%CC%A7a%20de%20aplicac%CC%A7o%CC%83es%20cloud-native%205eebd9add709497ca9e4442074ec8a79/Untitled%201.png)

Perceba que o canal de transmissão está protegido, o acesso a informação fica muito mais complexo para o atacante, isso exige muito mais esforço e minimiza consideravelmente as chances de termos informações expostas.

### 3 - Como rodar nossa aplicação com o mínimo de privilégios necessários? (Principle of Least Privilege {POLP} )

Garanta que sua aplicação utilize somente o conjunto de permissões que ela necessita. Exemplo: se sua aplicação precisar de acesso de leitura no banco de dados crie uma conta que permita somente leitura, não conceda privilégio de escrita sem a real necessidade. Esse tópico garante que nossa aplicação reduza o espaço ou brechas de segurança.

Essa prática reduz consideravelmente a área possível de ataques, de maneira que nossa aplicação não seja um ponto de exploração para possíveis ataques.

Conta de usuário com privilégio mínimo: Com o princípio do privilégio mínimo, um funcionário cujo trabalho é inserir informações em um banco de dados precisa apenas da capacidade de adicionar registros a esse banco de dados. Se o malware infectar o computador do funcionário ou se o funcionário clicar em um link em um e-mail de phishing, o ataque malicioso se limitará a fazer entradas no banco de dados. Se esse funcionário tiver privilégios de acesso root, no entanto, a infecção pode se espalhar por todo o sistema.

---

Imagine que estamos trabalhando com uma API que trata de dados de **cartão de crédito** de clientes de um banco. Supondo que essa aplicação será disponibilizada em um ambiente Cloud, quais cuidados devemos tomar em relação aos dados dos usuários processados pela aplicação?

Geralmente utilizamos ferramentas de log na construção de APIs para facilitar a identificação de comportamentos incorretos, bugs ou falhas na execução de funcionalidades. Quais cuidados devemos tomar em relação aos logs quando pensamos em segurança?

Sendo uma API, nossa aplicação se comunicará com outras. Pensando nisso descreva uma vulnerabilidade que podemos encontrar em relação a segurança e uma possível solução para ela.

E em relação ao banco de dados? Uma vez que estamos trabalhando com dados de cartão de crédito dos clientes e os mesmo serão persistidos, o que você imagina que pode ser feito para diminuir a possibilidade do vazamento dessas informações sensíveis ?

1. É importante, durante a criação da aplicação, nos atermos para o desenvolvimento da segurança a cada feature lançada. Com isso, minimizamos as falhas futuramente e deixamos ela mais segura para rodar em qualquer ambiente cloud.
2. Com relação aos Logs, devemos tomar cuidado com as informações sensíveis dos nossos clientes, como por exemplo, os dados dos cartões de crédito. Para isso, devemos ofuscar as informações para que, mesmo se um invasor tenha acesso aos logs, não poderá fazer muita coisa. Também é importante configurar para ofuscar os logs em caso de uso de um API Manager.
3. Quando nossa api está se comunicando com outro serviço, nossos dados estão trafegando livremente pela rede. Ou seja, qualquer invasor pode ter acesso a essas informações com muita facilidade! Por isso, é importante utilizar um canal de comunicação seguro. Dessa forma, o invasor terá muito mais dificuldade de acessar.
4. Para minimizar o vazamento de informações, é importante que nossa aplicação tenha o mínimo acesso necessário com o banco de dados. No nosso caso, somente ter acesso para persistir os dados. Dessa forma, não temos acesso a leitura dos dados e se invadirem o sistema, poderá apenas persistir dados e não ler todos os registros.