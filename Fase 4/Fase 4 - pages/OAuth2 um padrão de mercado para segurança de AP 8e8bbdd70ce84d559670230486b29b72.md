# OAuth2 um padrão de mercado para segurança de APIs REST!

Não queremos que qualquer usuário acesse nossa aplicação, por isso, precisamos de algum mecanismo que nos ajude a protegê-la.

Temos algumas maneiras de realizar essa tarefa: poderíamos usar o modelo mais simples com usuário e senha. Dessa maneira o usuário sempre que requisitar acesso a nossa aplicação deverá informar usuário + senha no Header da requisição.

Perceba que esse modelo não oferece uma segurança tão efetiva, porque em todos os requests informamos nossas credenciais, caso alguém consiga um acesso não autorizado a esses headers, nosso usuário e senha serão comprometidos.

Ainda existem alguns itens que precisam ser endereçados, como por exemplo, delegação de acesso. Claramente o modelo Basic de Autenticação não suporta delegação, nessa situação você precisaria informar a senha do seu Facebook, Google ou Github em cada acesso a site de terceiros, isso não parece ser adequado.

Precisamos delegar o acesso, podemos usar nossa credencial de Google, Facebook e Github mas não queremos informar nossa senha para um site terceiro, esse site terceiro deve requisitar ao provedor e então podemos continuar com nossa autenticação. Perceba que todo processo de autenticação acontece no nosso provedor de autenticação. Não há em nenhum momento troca de informação de credenciais entre site terceiro e provedor de autenticação.

## Entendendo as entidades do OAuth2

As 4 entidades são **Resource Owner**, Resource Server, Client e Authorization Server cada um deles executa uma atividade bem definida no processo de autenticação.

- *Client*: é quem quer consumir um recurso. Comumente uma aplicação;
- ***Resource owner***: é a entidade que é dona de um recurso, dona dos dados. Normalmente um usuário.
- *Authorization server*: é o servidor que processa toda a autorização. Quando você conecta um aplicativo ao Facebook, temos um servidor de autorização que vai verificar se o *client* tem permissão para acessar o recurso.
- *Resource server*: é o servidor que pode ceder o recurso que um *resource owner* cedeu a um *client* mediante autorização que foi bem-sucedida.