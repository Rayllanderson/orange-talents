# HTTP: Entendendo a web por baixo dos panos

## O que é Http? *Hypertext Transfer Protocol*

é um protocolo de comunicação. E um protocolo é um conjunto de regras.

*o HTTP foi feito para estabelecer regras de comunicação entre o modelo Cliente-Servidor que funciona na Web.*

> Se você compreende este texto, é porque você sabe português! Para que alguém consiga se comunicar com você, esse alguém deverá usar o português (supondo que você desconheça outro idioma, é claro). Isso significa que, sua regra (protocolo) de comunicação com o mundo é a língua portuguesa, que define a forma com que as informações devem chegar até você (através do vocabulário, regras de gramática e etc). Uma outra pessoa que conheça português irá usar do mesmo formato, já que vocês possuem um idioma em comum.
Na internet, como já vimos, o idioma mais comum é o HTTP. Ele é responsável por definir a forma de como os dados são trafegados na rede através de várias regras. Portanto, todo mundo que conhece o idioma HTTP poderá receber e enviar dados e participar dessa conversa!

## **O que você aprendeu nesse capítulo?**

- A arquitetura Cliente-Servidor
- Um protocolo é um conjunto de regras
- HTTP é um protocolo que define as regras de comunicação entre cliente e servidor na internet.
- HTTP é o protocolo mais importante da Internet

### O que é HTTPS?

Basicamente é o HTTP comum, porém com uma camada adicional de segurança/criptografia que antes era SSL, mas posteriormente passou a ser também TLS.

O HTTP transporta texto puro! Ou seja, qualquer um pode pegar esses dados. ex:

```json
[
	{
		"email": "ray@email.com",
		"password": "123"
	}

]
```

Qualquer um pode pegar esses dados até chegar no servidor.

Os dados só são criptografados quando utilizamos conexão segura ou seja utilizando o HTTPS;

E como faz pra uma página ter o HTTPS? Precisamos ter uma identidade confirmada, um certificado digital

> O HTTPS para garantir segurança usa criptografia baseada em chaves públicas e privadas e para gerar essas chaves publicas e privadas é preciso garantir a identidade de quem possui essas chaves e isso é feito a partir de um certificado digital, ou seja, um certificado digital é utilizado para identificar determinada entidade e ainda é utilizada para geração das chaves de criptografia.

- Por padrão, os dados são trafegados como texto puro na web.
- Apenas com HTTPS a Web é segura
- O protocolo HTTPS nada mais é do que o protocolo HTTP mais uma camada adicional de segurança, a TLS/SSL
- O tipo de criptografia de chave pública/chave privada
- O que são os certificados digitais
- Certificados possuem identidade e validade
- As chaves públicas estão no certificado, a chave privada fica apenas no servidor
- O que é uma autoridade certificadora
- O navegador utiliza a chave pública para criptografar os dados

O **DNS** realiza a tradução do nome de um domínio para o endereço de IP. Existem vários servidores DNS no mundo e é fundamental para a nossa web o funcionamento deles.

A porta reservada para o protocolo HTTP é o `80`.

Já o protocolo HTTPS possui uma porta padrão, a porta `443`.

- **URL** são os endereços da Web
- Uma URL começa com o protocolo (por exemplo `https://`) seguido pelo **domínio** (`www.alura.com.br`)
- Depois do domínio pode vir a porta, se não for definida é utilizada a porta padrão desse protocolo
- Após o *domínio:porta*, é especificado o **caminho** para um **recurso** (`/course/introducao-html-css`)
- Um **recurso** é algo concreto na aplicação que queremos acessar

![HTTP%20Entendendo%20a%20web%20por%20baixo%20dos%20panos%2019815eeba03b43e09b3ecbe903fe6cd0/Untitled.png](HTTP%20Entendendo%20a%20web%20por%20baixo%20dos%20panos%2019815eeba03b43e09b3ecbe903fe6cd0/Untitled.png)

- O HTTP usa sessões para salvar informações do usuário
- Sessões só são possíveis por uso de Cookies
- Cookies são pequenos arquivos que guardam informações no navegador
- O HTTP é stateless, não mantem estado.

> Uma sessão HTTP nada mais é que um tempo que o cliente permanece ativo no sistema! Isso é parecido com uma sessão no cinema. Uma sessão, nesse contexto, é o tempo que o cliente usa a sala no cinema para assistir a um filme. Quando você sai da sala, termina a sessão. Ou seja, quando você se desloga, a Alura termina a sua sessão.

> Um cookie é um pequeno arquivo de texto, normalmente criado pela aplicação web, para guardar algumas informações sobre usuário no navegador. Quais são essas informações depende um pouco da aplicação. Pode ser que fique gravado alguma preferência do usuário. Ou algumas informações sobre as compras na loja virtual ou, como vimos no vídeo, a identificação do usuário. Isso depende da utilidade para a aplicação web.

Utilizando o método GET, tanto o login quanto a senha seriam passados como parâmetro na URL, coisa que não queremos que aconteça. O método POST deixa os parâmetros no corpo da requisição, assim evita que informações importantes, como a senha, fiquem explícitas na URL.

Mas como especificar à aplicação de serviço que gostaríamos de receber em um formato JSON? Via cabeçalho HTTP!

Para indicar que queremos resposta no formato JSON usa-se um Accept: application/json como cabeçalho HTTP. Por outro lado já na resposta uma indicação desse conteúdo é especificado pelo cabeçalho Content-Type: application/json.

De que forma podemos especificar o formato que esperamos que seja devolvido?

A resposta correta é usando o cabeçalho **Accept**.

Através dele podemos usar algum formato específico como:

```
Accept: text/html, Accept: text/css, Accept: application/xml, Accept: application/json, Accept:image/jpeg e Accept: image/*
```

Ou até mesmo não definir nenhum formato específico colocando:

```
Accept: */*
```

Nossas URIs devem representar recursos, as operações no recurso devem ser indicadas pelos métodos HTTP e podemos falar qual é o formato em que conversamos com o servidor com o Content-Type e Accept que são cabeçalhos do HTTP.

### Atividade proposta

Você ficou responsável por definir o melhor uso do protocolo HTTP para possibilitar a criação de uma API que realiza pesquisas de passagens de avião e cadastro de compradores.

Para a parte de pesquisa, nos diga qual verbo http você vai utilizar e também como vai passar os argumentos. Além disso, é necessário que você explique o motivo da decisão.

Para a parte de cadastro, nos diga qual verbo http você vai utilizar e também como vai passar os argumentos. Outro ponto importante é:  Esse endereço pode receber dados via formulário normal(form-url-encoded) ou JSON. Como você vai adicionar essa flexibilidade? Explique o motivo da decisão.

Para fechar, é necessário que sempre que possível a comunicação seja feita com os dados criptografados. O que você sugeriria aqui e por que?

1. Para a parte de pesquisa, o verbo Http utilizado será o GET, pois ele geralmente é destinado para recuperar informações. Para passagem de argumentos, eu vou usar os parâmetros da requisição, que ficarão visíveis na URL, sendo fácil de guardar essa URL caso precise futuramente.
2. Para o cadastro, o verbo Http utilizado será o POST, pois as credenciais do usuário não deverão ficar nos parâmetros da requisição. Para ter a flexibilidade de receber os dados normais ou em Json, eu adiciono no Header da requisição: Content-Type: application/x-www-form-urlencoded ou application/json, para dizer ao servidor que tipo de dado estou enviando.
3. Para comunicação segura, utilizo o HTTPS no lugar do HTTP, pois o HTTPS possui uma camada adicional de segurança/criptografia, dessa forma, os dados trafegarão de forma segura, sem risco de serem roubados e entendidos por outros de fora. Além disso, caso uma requisição seja feita em HTTP, redirecionaria, sempre, para o HTTPS.