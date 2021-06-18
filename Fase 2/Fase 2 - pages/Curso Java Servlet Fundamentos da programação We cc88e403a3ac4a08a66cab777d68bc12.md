# Curso | Java Servlet: Fundamentos da programação Web Java

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