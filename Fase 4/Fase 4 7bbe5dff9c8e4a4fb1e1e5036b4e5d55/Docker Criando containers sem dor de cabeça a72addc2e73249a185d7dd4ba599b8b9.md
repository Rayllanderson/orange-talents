# Docker: Criando containers sem dor de cabeça

## **A evolução do host de aplicações**

Antigamente, quando queríamos montar o nosso sistema, com vários serviços (bancos de dados, proxy, etc) e aplicações, acabávamos tendo vários servidores físicos, cada um com um serviço ou aplicação do nosso sistema.

E claro, não conseguimos instalar os serviços diretamente no hardware do servidor, ou seja, precisamos de um intermediário entre as aplicações e o hardware, que é o sistema operacional. Ou seja, devemos instalar sistemas operacionais em cada servidor, e os sistemas poderiam ser diferentes:

![Docker%20Criando%20containers%20sem%20dor%20de%20cabec%CC%A7a%20a72addc2e73249a185d7dd4ba599b8b9/Untitled.png](Docker%20Criando%20containers%20sem%20dor%20de%20cabec%CC%A7a%20a72addc2e73249a185d7dd4ba599b8b9/Untitled.png)

E se quisermos que uma aplicação se comunique com outra ou faça qualquer comunicação externa, devemos conectar os servidores à rede. Além disso, para eles funcionarem, precisamos ligá-los à eletricidade. Logo, havia diversos custos de eletricidade, rede e configuração dos servidores.

### Capacidade pouco aproveitada

Era muito comum termos servidores parrudos, com uma única aplicação sendo executada, para normalmente ficarem funcionando abaixo da sua capacidade, para quando for necessário, o servidor aguentar uma grande quantidade de acessos. Isso resultava em muita capacidade ociosa nos servidores, com muitos recursos desperdiçados.

### **Virtualização, uma solução?**

Para fugir desses problemas de servidores ociosos e alto tempo e custo de subir e manter aplicações em servidores físicos, surgiu como solução a **virtualização**, surgindo assim as **máquinas virtuais**.

![Docker%20Criando%20containers%20sem%20dor%20de%20cabec%CC%A7a%20a72addc2e73249a185d7dd4ba599b8b9/Untitled%201.png](Docker%20Criando%20containers%20sem%20dor%20de%20cabec%CC%A7a%20a72addc2e73249a185d7dd4ba599b8b9/Untitled%201.png)

Assim, reduzimos a quantidade de servidores e consequentemente os custos com luz e rede. Além disso, dividimos melhor o nosso hardware, aproveitando melhor os seus recursos e diminuindo a ociosidade.

### Problemas das máquinas virtuais

Apesar de resolver os problemas do uso de vários servidores físicos, as máquinas virtuais também possuem problemas. Para podermos hospedar a nossa aplicação em uma máquina virtual, também precisamos instalar um sistema operacional nela.

Cada aplicação necessita de um sistema operacional para poder ser executada, e esses sistemas podem ser diferentes. E apesar dos sistemas operacionais serem um software, eles possuem um custo de memória RAM, disco e processamento. Ou seja, precisamos de uma capacidade mínima para manter as funcionalidades de um sistema operacional, aumentando o seu custo de manutenção a cada sistema que tivermos.

### Os Containners

Um container funcionará junto do nosso sistema operacional base, e conterá a nossa aplicação, ou seja, a aplicação será executada dentro dele. Criamos um container para cada aplicação, e esses containers vão dividir as funcionalidades do sistema operacional:

![Docker%20Criando%20containers%20sem%20dor%20de%20cabec%CC%A7a%20a72addc2e73249a185d7dd4ba599b8b9/Untitled%202.png](Docker%20Criando%20containers%20sem%20dor%20de%20cabec%CC%A7a%20a72addc2e73249a185d7dd4ba599b8b9/Untitled%202.png)

Não temos mais um sistema operacional para cada aplicação, já que agora as aplicações estão dividindo o mesmo sistema operacional, que está em cima do nosso hardware. Os próprios *containers* terão a lógica que se encarregará dessa divisão.

Assim, com somente um sistema operacional, reduzimos os custos de manutenção e de infraestrutura como um todo.

### **Vantagens de um container**

Por não possuir um sistema operacional, o *container* é muito mais leve e não possui o custo de manter múltiplos sistemas operacionais, já que só teremos um sistema operacional, que será dividido entre os *containers*.

### **Necessidade do uso de containers**

Mas por que precisamos dos *containers*, não podemos simplesmente instalar as aplicações no nosso próprio sistema operacional? Até por que já fazemos isso, já que no nosso sistema operacional temos um editor de texto, terminal, navegador, etc.

No caso das nossas aplicações, essa abordagem pode ter alguns problemas. Por exemplo, se dois aplicativos precisarem utilizar a mesma porta de rede? Precisaremos de algo para isolar uma aplicação da outra. E se uma aplicação consumir toda a CPU, a ponto de prejudicar o funcionamento das outras aplicações? Isso acontece se não limitarmos a aplicação. Outro problema que pode ocorrer é cada aplicação precisar de uma versão específica de uma linguagem, por exemplo, uma aplicação precisa do Java 7 e outra do Java 8. Além disso, uma aplicação pode acabar congelando todo o sistema. Por isso é bom ter essa separação das aplicações, isolar uma da outra, e isso pode ser feito com os ***containers***.

Com os *containers*, conseguimos limitar o consumo de CPU das aplicações, melhorando o controle sobre o uso de cada recurso do nosso sistema (CPU, rede, etc). Também temos uma facilidade maior em trabalhar com versões específicas de linguagens/bibliotecas, além de ter uma agilidade maior na hora de criar e subir *containers*, já que eles são mais leves que as máquinas virtuais.

### Comando Docker

- `docker version` **** exibe a versão do docker.
- `docker run NOME_DA_IMAGEM` **** cria um container com a respectiva imagem passada como parâmetro.
- `docker ps` **** exibe todos os containers em execução no momento.
- `docker ps -a` **** exibe todos os containers, independentemente de estarem em execução ou não.
- `docker run -it NOME_DA_IMAGEM` **** conecta o terminal que estamos utilizando com o do container.
- `docker start ID_CONTAINER` **** inicia o container com id em questão.
- `docker stop ID_CONTAINER` **** interrompe o container com id em questão.
- `docker start -a -i ID_CONTAINER` **** inicia o container com id em questão e integra os terminais, além de permitir interação entre ambos.
- `docker rm ID_CONTAINER` **** remove o container com id em questão.
- `docker container prune` **** remove todos os containers que estão parados.
- `docker rmi NOME_DA_IMAGEM` **** remove a imagem passada como parâmetro.
- `docker run -d -P --name NOME dockersamples/static-site` **** ao executar, dá um nome ao container.
- `docker run -d -p 12345:80 dockersamples/static-site` **** define uma porta específica para ser atribuída à porta 80 do container, neste caso 12345.
- `docker run -d -P -e AUTHOR="Fulano" dockersamples/static-site` **** define uma variável de ambiente AUTHOR com o valor *Fulano* no container criado.
- `docker run -v "[CAMINHO_VOLUME_LOCAL:]CAMINHO_VOLUME_CONTAINER" NOME_DA_IMAGEM` **** cria um volume no respectivo caminho do container, caso seja especificado um caminho local monta o volume local no volume do container.
- `docker inspect ID_CONTAINER` **** retorna diversas informações sobre o container.

```powershell
docker run -p 8080:3000 -v "C:\Users\Alura\Desktop\volume-exemplo:/var/www" 
-w "/var/www" node npm start
```

Como o nosso projeto precisa do Node.js, vamos utilizar a sua imagem:

```docker
FROM node
```

Além disso, podemos indicar a versão da imagem que queremos, ou utilizar o **`latest`**, que faz referência à versão mais recente da imagem. Se não passarmos versão nenhuma, o Docker irá assumir que queremos o **`latest`**, mas vamos deixar isso explícito:

```docker
FROM node:latest
```

Outra instrução que é comum colocarmos é quem cuida, quem criou a imagem, através do **`MAINTAINER`**:

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
```

Agora, especificamos o que queremos na imagem. No caso, queremos colocar o nosso código dentro da imagem, então utilizarmos o **`COPY`**. Como queremos copiar tudo o que está dentro da pasta, vamos utilizar o **`.`** para copiar tudo que está na pasta do arquivo **Dockerfile**, e vamos copiar para **/var/www**, do exemplo da aula anterior:

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
COPY . /var/www
```

No projeto, já temos as suas dependências dentro da pasta **node_modules**, mas não queremos copiar essa pasta para dentro do *container*, pois elas podem estar desatualizadas, quebradas, então queremos que a própria imagem instale as dependências para nós, rodando o comando **`npm install`**. Para executar um comando, utilizamos o **`RUN`**:

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
COPY . /var/www
RUN npm install
```

Agora, **deletamos a pasta node_modules**, para ela não ser copiada para o *container*. Além disso, toda imagem possui um comando que é executado quando a mesma inicia, e o comando que utilizamos na aula anterior foi o **`npm start`**. Para isso, utilizamos o **`ENTRYPOINT`**, que executará o comando que quisermos assim que o *container* for carregado:

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
COPY . /var/www
RUN npm install
ENTRYPOINT npm start
```

Também podemos passar o comando como se fosse em um array, por exemplo **`["npm", "start"]`**, ambos funcionam.

Falta colocarmos a porta em que a aplicação executará, a porta em que ela ficará exposta. Para isso, utilizamos o **`EXPOSE`**:

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
COPY . /var/www
RUN npm install
ENTRYPOINT ["npm", "start"]
EXPOSE 3000
```

Por fim, falta dizermos onde os comandos rodarão, pois eles devem ser executados dentro da pasta **`/var/www`**. Então, através do **`WORKDIR`**, assim que copiarmos o projeto, dizemos em qual diretório iremos trabalhar;

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
COPY . /var/www
WORKDIR /var/www
RUN npm install
ENTRYPOINT npm start
EXPOSE 3000
```

Com isso, finalizamos o **Dockerfile**, baseado no comando que fizemos na aula anterior:docker run -p 8080:3000 -v "$(pwd):/var/www" -w "/var/www" node npm start

```powershell
docker run -p 8080:3000 -v "$(pwd):/var/www" -w "/var/www" node npm start
```

Resta agora criar a imagem.

## **Criando a imagem**

Para criar a imagem, precisamos fazer o seu *build* através do comando **`docker build`**, comando utilizado para *buildar* uma imagem a partir de um **Dockerfile**. Para configurar esse comando, passamos o nome do **Dockerfile** através da *flag* **`-f`**:

```powershell
docker build -f Dockerfile
```

Como o nome do nosso **Dockerfile** é o padrão, poderíamos omitir esse parâmetro, mas se o nome for diferente, por exemplo **node.dockerfile**, é preciso especificar, mas vamos deixar especificado para detalharmos melhor o comando.

Além disso, passamos a *tag* da imagem, o seu nome, através da *flag* **`-t`**. Já vimos que para imagens não-oficiais, colocamos o nome no padrão **NOME_DO_USUARIO/NOME_DA_IMAGEM**, então é isso que faremos, por exemplo:

```powershell
docker build -f Dockerfile -t douglasq/node
```

E agora dizemos onde está o **Dockerfile**. Como já estamos rodando o comando dentro da pasta **volume-exemplo**, vamos utilizar o ponto (**`.`**);

```powershell
docker build -f Dockerfile -t douglasq/node .
```

Ao executar o comando, podemos perceber que cada instrução executada do nosso **Dockerfile** possui um **id**. Isso por que para cada passo o Docker cria um *container* intermediário, para se aproveitar do seu sistema de camadas. Ou seja, cada instrução gera uma nova camada, que fará parte da imagem final, que nada mais é do que a imagem-base com vários *containers* intermediários em cima, sendo que cada um desses *containers* representa um comando do **Dockerfile**.

Assim, se um dia a imagem precisar ser alterada, somente o *container* referente à instrução modificada será alterado, com as outras partes intermediárias da imagem já prontas.

## **Criando um container a partir da nossa imagem**

Agora que já temos a imagem criada, podemos criar um *container* a partir dela:

```powershell
docker run -d -p 8080:3000 douglasq/node
```

Ao acessar o endereço da porta no navegador, vemos a página da nossa aplicação. No **Dockerfile**, também podemos criar variáveis de ambiente, utilizando o **`ENV`**. Por exemplo, para criar a variável **`PORT`**, para dizer em que porta a nossa aplicação irá rodar, fazemos:

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
ENV PORT=3000
COPY . /var/www
WORKDIR /var/www
RUN npm install
ENTRYPOINT npm start
EXPOSE 3000
```

E no próprio **Dockerfile**, podemos utilizar essa variável:

```docker
FROM node:latest
MAINTAINER Douglas Quintanilha
ENV PORT=3000
COPY . /var/www
WORKDIR /var/www
RUN npm install
ENTRYPOINT npm start
EXPOSE $PORT
```

E como modificamos o **Dockerfile**, precisamos construir a nossa imagem novamente e podemos perceber que dessa vez o comando é bem mais rápido, já que quase todas as camadas estão *cacheadas* pelo Docker.

### Subindo a imagem pro docker hub

Já criamos a imagem, mas por enquanto ela só está na nossa máquina local. Para disponibilizar a imagem para outras pessoas, precisamos enviá-la para o **[Docker Hub](https://hub.docker.com/)**.

O primeiro passo é criar a nossa conta. Com ela criada, no terminal nós executamos o comando **`docker login`** e digitamos o nosso login e senha que acabamos de criar.

Após isso, basta executar o comando **`docker push`**, passando para ele a imagem que queremos subir, por exemplo:

```powershell
docker push douglasq/node
```

Esse comando pode demorar um pouco, mas terminada a sua execução, podemos ver que várias mensagens ***Mounted from library/node***, ou seja, o Docker já sabe que essas camadas podem ser reaproveitadas da imagem do **node**, então não tem o porquê dessas camadas subirem também, então só as camadas diferentes são enviadas para o **Docker Hub**.

Mais uma vantagem em se trabalhar com camadas, o ***Layered File System***, pois até na hora de fazer o upload, só é feito das camadas diferentes, as outras são referenciadas da imagem-base que estamos utilizando, no caso a do **node**.

Por fim, ao acessar a nossa conta do **Docker Hub**, podemos ver que a imagem está lá. Para baixá-la, podemos utilizar o comando **`docker pull`**:

```powershell
docker pull douglasq/node
```

Esse comando somente baixa a imagem, sem criar nenhum *container* acima dela.

Então, esse é um jeito de simples de compartilharmos uma imagem com outras pessoas, através do **Docker Hub**. A imagem é disponibilizada em um repositório público, mas também podemos disponibilizar em repositórios privados, que no momento da criação do curso, cada usuário pode criar um repositório privado gratuitamente.

Aprendemos neste capítulo:

- A entender o papel do arquivo DockerFile para criar imagens.
    - O Dockerfile define os comandos para executar instalações complexas e com características específicas.
- Vimos os principais comandos como `FROM`, `MAINTAINER`, `COPY`, `WORKDIR`, `RUN`, `EXPOSE` e `ENTRYPOINT`
- A subir uma imagem criada através de um Dockerfile para o Docker Hub e disponibilizar para os desenvolvedores

Lembrando também:

- as imagens são *read-only* sempre
- um container é uma instância de uma imagem
- para guardar as alterações a *docker engine* cria uma nova layer em cima da última layer da imagem

Segue também uma breve lista dos comandos utilizados:

- `docker build -f Dockerfile` **** cria uma imagem a partir de um Dockerfile.
- `docker build -f CAMINHO_DOCKERFILE/Dockerfile -t NOME_USUARIO/NOME_IMAGEM` **** constrói e nomeia uma imagem não-oficial informando o caminho para o Dockerfile.
- `docker login` **** inicia o processo de login no Docker Hub.
- `docker push NOME_USUARIO/NOME_IMAGEM` **** envia a imagem criada para o Docker Hub.
- `docker pull NOME_USUARIO/NOME_IMAGEM` **** baixa a imagem desejada do Docker Hub.

## **Criando a nossa própria rede do Docker**

Então, vamos criar a nossa própria rede, através do comando **`docker network create`**, mas não é só isso, para esse comando também precisamos dizer qual *driver* vamos utilizar. Para o padrão que vimos, de ter uma nuvem e os *containers* compartilhando a rede, devemos utilizar o *driver* de ***bridge***.

Especificamos o driver através do **`--driver`** e após isso nós dizemos o nome da rede. Um exemplo do comando é o seguinte:

```bash
docker network create --driver bridge minha-rede
```

Agora, quando criamos um *container*, ao invés de deixarmos ele ser associado à rede padrão do Docker, atrelamos à rede que acabamos de criar, através da *flag* **`--network`**. Vamos aproveitar e nomear o *container*:

```powershell
docker run -it --name meu-container-de-ubuntu --network minha-rede ubuntu
```

Agora, se executarmos o comando **`docker inspect meu-container-de-ubuntu`**, podemos ver em ***NetworkSettings*** o *container* está na rede **minha-rede**. E para testar a comunicação entre os *containers* na nossa rede, vamos abrir outro terminal e criar um segundo *container*:

```powershell
docker run -it --name segundo-ubuntu --network minha-rede ubuntu
```

Agora, no **segundo-ubuntu**, instalamos o **ping** e testamos a comunicação com o **meu-container-de-ubuntu**:

```powershell
root@00f93075d079:/# ping meu-container-de-ubuntu
PING meu-container-de-ubuntu (172.18.0.2) 56(84) bytes of data.
64 bytes from meu-container-de-ubuntu.minha-rede (172.18.0.2): icmp_seq=1 ttl=64 time=0.210 ms
64 bytes from meu-container-de-ubuntu.minha-rede (172.18.0.2): icmp_seq=2 ttl=64 time=0.148 ms
64 bytes from meu-container-de-ubuntu.minha-rede (172.18.0.2): icmp_seq=3 ttl=64 time=0.138 ms
^C
--- meu-container-de-ubuntu ping statistics ---
3 packets transmitted, 3 received, 0% packet loss, time 2000ms
rtt min/avg/max/mdev = 0.138/0.165/0.210/0.033 ms
```

Conseguimos realizar a comunicação entre os *containers* utilizando somente os seus nomes. É como se o **Docker Host**, o ambiente que está rodando os *containers*, criasse uma rede local chamada **minha-rede**, e o nome do *container* será utilizado como se fosse um *hostname*.

Mas lembrando que só conseguimos fazer isso em redes próprias, redes que criamos, isso não é possível na rede padrão dos *containers*.

Neste capítulo aprendemos:

- Que imagens criadas pelo Docker acessam a mesma rede, porém apenas através de IP.
- Ao criar uma rede é possível realizar a comunicação entre os containers através do seu nome.
- Que durante a criação de uma rede precisamos indicar qual driver utilizaremos, geralmente, o driver `bridge`.

Segue também uma breve lista dos comandos utilizados:

- `hostname -i` **** mostra o ip atribuído ao container pelo docker (funciona apenas dentro do container).
- `docker network create --driver bridge NOME_DA_REDE` **** cria uma rede especificando o driver desejado.
- `docker run -it --name NOME_CONTAINER --network NOME_DA_REDE NOME_IMAGEM` **** cria um container especificando seu nome e qual rede deverá ser usada.

Segue a lista com os principais comandos utilizados durante o curso:

- Comandos relacionados às informações
    - `docker version` **** exibe a versão do docker que está instalada.
    - `docker inspect ID_CONTAINER` **** retorna diversas informações sobre o container.
    - `docker ps` **** exibe todos os containers em execução no momento.
    - `docker ps -a` **** exibe todos os containers, independentemente de estarem em execução ou não.
- Comandos relacionados à execução
    - `docker run NOME_DA_IMAGEM` **** cria um container com a respectiva imagem passada como parâmetro.
    - `docker run -it NOME_DA_IMAGEM` **** conecta o terminal que estamos utilizando com o do container.
    - `docker run -d -P --name NOME dockersamples/static-site` **** ao executar, dá um nome ao container e define uma porta aleatória.
    - `docker run -d -p 12345:80 dockersamples/static-site` **** define uma porta específica para ser atribuída à porta 80 do container, neste caso 12345.
    - `docker run -v "CAMINHO_VOLUME" NOME_DA_IMAGEM` **** cria um volume no respectivo caminho do container.
    - `docker run -it --name NOME_CONTAINER --network NOME_DA_REDE NOME_IMAGEM` **** cria um container especificando seu nome e qual rede deverá ser usada.
- Comandos relacionados à inicialização/interrupção
    - `docker start ID_CONTAINER` **** inicia o container com id em questão.
    - `docker start -a -i ID_CONTAINER` **** inicia o container com id em questão e integra os terminais, além de permitir interação entre ambos.
    - `docker stop ID_CONTAINER` **** interrompe o container com id em questão.
- Comandos relacionados à remoção
    - `docker rm ID_CONTAINER` **** remove o container com id em questão.
    - `docker container prune` **** remove todos os containers que estão parados.
    - `docker rmi NOME_DA_IMAGEM` **** remove a imagem passada como parâmetro.
- Comandos relacionados à construção de Dockerfile
    - `docker build -f Dockerfile` **** cria uma imagem a partir de um Dockerfile.
    - `docker build -f Dockerfile -t NOME_USUARIO/NOME_IMAGEM` **** constrói e nomeia uma imagem não-oficial.
    - `docker build -f Dockerfile -t NOME_USUARIO/NOME_IMAGEM CAMINHO_DOCKERFILE` **** constrói e nomeia uma imagem não-oficial informando o caminho para o Dockerfile.
- Comandos relacionados ao Docker Hub
    - `docker login` **** inicia o processo de login no Docker Hub.
    - `docker push NOME_USUARIO/NOME_IMAGEM` **** envia a imagem criada para o Docker Hub.
    - `docker pull NOME_USUARIO/NOME_IMAGEM` **** baixa a imagem desejada do Docker Hub.
- Comandos relacionados à rede
    - `hostname -i` **** mostra o ip atribuído ao container pelo docker (funciona apenas dentro do container).
    - `docker network create --driver bridge NOME_DA_REDE` **** cria uma rede especificando o driver desejado.
- Comandos relacionados ao docker-compose
    - `docker-compose build` **** Realiza o build dos serviços relacionados ao arquivo docker-compose.yml, assim como verifica a sua sintaxe.
    - `docker-compose up` **** Sobe todos os containers relacionados ao docker-compose, desde que o build já tenha sido executado.
    - `docker-compose down` **** Para todos os serviços em execução que estejam relacionados ao arquivo docker-compose.yml.

    Segue a lista com os principais comandos utilizados durante o curso:

    - Comandos relacionados às informações
        - `docker version` **** exibe a versão do docker que está instalada.
        - `docker inspect ID_CONTAINER` **** retorna diversas informações sobre o container.
        - `docker ps` **** exibe todos os containers em execução no momento.
        - `docker ps -a` **** exibe todos os containers, independentemente de estarem em execução ou não.
    - Comandos relacionados à execução
        - `docker run NOME_DA_IMAGEM` **** cria um container com a respectiva imagem passada como parâmetro.
        - `docker run -it NOME_DA_IMAGEM` **** conecta o terminal que estamos utilizando com o do container.
        - `docker run -d -P --name NOME dockersamples/static-site` **** ao executar, dá um nome ao container e define uma porta aleatória.
        - `docker run -d -p 12345:80 dockersamples/static-site` **** define uma porta específica para ser atribuída à porta 80 do container, neste caso 12345.
        - `docker run -v "CAMINHO_VOLUME" NOME_DA_IMAGEM` **** cria um volume no respectivo caminho do container.
        - `docker run -it --name NOME_CONTAINER --network NOME_DA_REDE NOME_IMAGEM` **** cria um container especificando seu nome e qual rede deverá ser usada.
    - Comandos relacionados à inicialização/interrupção
        - `docker start ID_CONTAINER` **** inicia o container com id em questão.
        - `docker start -a -i ID_CONTAINER` **** inicia o container com id em questão e integra os terminais, além de permitir interação entre ambos.
        - `docker stop ID_CONTAINER` **** interrompe o container com id em questão.
    - Comandos relacionados à remoção
        - `docker rm ID_CONTAINER` **** remove o container com id em questão.
        - `docker container prune` **** remove todos os containers que estão parados.
        - `docker rmi NOME_DA_IMAGEM` **** remove a imagem passada como parâmetro.
    - Comandos relacionados à construção de Dockerfile
        - `docker build -f Dockerfile` **** cria uma imagem a partir de um Dockerfile.
        - `docker build -f Dockerfile -t NOME_USUARIO/NOME_IMAGEM` **** constrói e nomeia uma imagem não-oficial.
        - `docker build -f Dockerfile -t NOME_USUARIO/NOME_IMAGEM CAMINHO_DOCKERFILE` **** constrói e nomeia uma imagem não-oficial informando o caminho para o Dockerfile.
    - Comandos relacionados ao Docker Hub
        - `docker login` **** inicia o processo de login no Docker Hub.
        - `docker push NOME_USUARIO/NOME_IMAGEM` **** envia a imagem criada para o Docker Hub.
        - `docker pull NOME_USUARIO/NOME_IMAGEM` **** baixa a imagem desejada do Docker Hub.
    - Comandos relacionados à rede
        - `hostname -i` **** mostra o ip atribuído ao container pelo docker (funciona apenas dentro do container).
        - `docker network create --driver bridge NOME_DA_REDE` **** cria uma rede especificando o driver desejado.
    - Comandos relacionados ao docker-compose
        - `docker-compose build` **** Realiza o build dos serviços relacionados ao arquivo docker-compose.yml, assim como verifica a sua sintaxe.
        - `docker-compose up` **** Sobe todos os containers relacionados ao docker-compose, desde que o build já tenha sido executado.
        - `docker-compose down` **** Para todos os serviços em execução que estejam relacionados ao arquivo docker-compose.yml.

        ### Atividade proposta

        Agora que você já desenvolveu os sistemas que simulam a Casa do Código e o Mercado Livre imagine que é necessário preparar essas aplicações para serem executadas em um ambiente real, seja por você mesmo ou por outra equipe da sua empresa.

        Sabendo que já existe um ambiente preparado com Docker, como você faria para gerar as imagens referentes a cada uma das aplicações? Cada uma das aplicações também precisa de um banco de dados para funcionar corretamente, o que você faria para garantir a existência do banco de dados no momento da criação dos contêineres a partir das imagens criadas?

        Lembrando que ambas as aplicações estão configuradas para utilizarem a porta 8080, existe algum problema ao subir contêineres de ambas as imagens criadas ao mesmo tempo?

        E como você faria para executar as imagens criadas?

        1. Criaria um dockerfile para cada projeto Spring boot.
        2. Realizaria o psuh dessas imagens para o dockerhub com `docker push NOME_USUARIO/NOME_IMAGEM` para que essas imagens fiquem disponíveis para qualquer um com docker configurado
        3. Agora, para acessar basta apenas dar um pull com `docker push NOME_USUARIO/NOME_IMAGEM` e elas estarão prontas para uso.
        4. Como o nosso projeto precisa de um banco de dados, pra subir nossas imagens, eu usaria o docker-compose para me ajudar a subir meus containers.
        5. No docker-compose, eu configuraria um banco de dados, uma network e os nossos 2 projetos configurados nessa rede que será criada. Também iremos colocar os 2 projetos para executarem depois que o banco subir. Como ambos os projetos rodam na porta 8080 por padrão, iremos configurar isso também no docker compose, fazendo com que um suba na porta 80:80 e o outro na porta 80:81