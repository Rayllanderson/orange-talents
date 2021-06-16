# Fase 2

Nesta fase você fará uma imersão teórica sobre desenvolvimento web utilizando o paradigma de programação orientado a objetos com Java, Spring e Hibernate.

- Design de código orientado a objetos com a linguagem Java
- SQL básico para realizar as operações de leitura e escrita necessárias
- Spring MVC para facilitar na criação da camada web da aplicação
- Hibernate para fazer o mapeamento entre o mundo orientado a objetos e relacional
- Design de código orientado a objetos com a linguagem Java
- Spring Boot para facilitar a utilização de tecnologias do ecossistemas Spring
- Spring Validation para facilitar no processo de validação da entrada dos dados

### Curso | Java Servlet: Fundamentos da programação Web Java

### Atividade proposta

Neste projeto vai ser necessário que você cadastre numa lista em memória um novo autor(a). Cada autor(a) tem um nome e um email.

Importante: Você não precisa criar esse código no seu editor de código. Apenas deixe aqui os passos que faria na implementação.

Para te ajudar: Qual classe você criaria? De quem ela herdaria? Qual método você vai sobrescrever? Vai usar um get ou um post para receber os dados? Qual redirecionamento vai utilizar? E claro que você deve acrescentar todo o resto para completar a funcionalidade.

1 - Baixaria e configuraria o tomcat na IDE.

2 - Criaria o projeto como um projeto web e configuraria o tomcat no projeto;

3 - Agora, com tudo configurado, eu criaria nossa entidade / model do Autor com os atributos: id, nome e email;

4 - Criaria a nossa Servlet chamada de `RegisterAuthorServlet`, que ficaria encarregada das requisições Http, de recuperar e modelar nossos objetos antes de salvar. Para criação da Servlet, eu herdaria o objeto HttpServlet e para o mapeamento, anotaria a mesma com `@WebServlet(urlPatterns = { "/register-author"})`

5 - Agora eu criaria uma JSP que ficaria responsável pelo form html da nossa aplicação. Nela teriam os seguintes campos: input de email, de nome e um botão de enviar para submeter o form;

6 - Configuraria o form. O método que usaria seria o POST e mapearia para nossa servlet de registro. Nosso form ficaria desta forma: `<form action="${/nome-do-projeto/register-author}" method="post"> ... </form>`;

7 - Em "`RegisterAuthorServlet`", sobrescreveria o método doPost;

8 - Dentro do doPost, recuperaria e modelaria os dados. Por exemplo: Recuperando - `String authorName = (String) request.getParameter("author-name");`Modelando - `author.setName(authorName);`

9 - Com os dados recuperados e o objeto Author modelado, precisamos persistir. Para isso, criaria a classe service.  Ela seria a nossa classe de serviço, que tomaria conta das operações de CRUD. Ah, como o cadastro seria em memória, então criaria uma lista estática de autor: `private static List<Author> authors = new ArrayList<>();`;

10 - Instanciaria o Service na nossa servlet de registro, "`RegisterAuthorServlet`";

11 - Persistira o author. Exemplo: `service.register(authorToBeRegistered);`;

12 - Antes de continuar, eu criaria outro Servlet chamada de "`ListAuthorServlet`" que ficaria responsável por listar os autores cadastrados. Os passos de criação seguiriam os mesmos do "`RegisterAuthorServlet`" e o mapeamento seria `/list-authors`. 

13 - Instanciaria o Service em "`ListAuthorServlet`";

14 - Em "`ListAuthorServlet`", sobrescreveria o método doGet;

15 - No método doGet, usaria o método `findAll()` do nosso service para retornar todos os autores cadastrados e setaria os mesmos em um atributo para serem listados no JSP de listagem. Exemplo: `request.setAttribute("authors", service.findAll());`;

16 - Prepararia o redirecionamento para uma JSP de listagem de autores -que ainda não foi criada-. Exemplo: `request.getRequestDispatcher("/list-authors.jsp").forward(request, response);`;

17 - Criaria o JSP de listagem dos autores e prepararia a mesma para receber os dados da servlet "`ListAuthorServlet`" com o `jstl/core`. Exemplo do forEach: `<c:forEach var="author" items="${authors}"> ... </c:forEach>` ;

18 - Com tudo pronto, voltaria para "`RegisterAuthorServlet`". Após persistir com sucesso, realizaria um redirecionamento pelo navegador para que o mesmo redirecione para a servlet "`ListAuthorServlet`". O redirecionamento ficaria assim:  `response.sendRedirect("list-authors");`.

 19 - Caindo no "`ListAuthorServlet`", ele executaria o método doGet, que, por sua vez, redirecionaria para a JSP de listagem de todos os autores.

## HTTP: Entendendo a web por baixo dos panos

O que é Http? *Hypertext Transfer Protocol*

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

![Fase%202%20c2183ffadeb641b58595ee19b8938c69/Untitled.png](Fase%202%20c2183ffadeb641b58595ee19b8938c69/Untitled.png)

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