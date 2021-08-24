# Kubernetes

- O problema que o Kubernetes se propõe a resolver

    Imagina uma máquina normal, esse meu notebook aqui. Subo meus containers de boas, subo um postgres, uma aplicação spring... belê... Chega um momento em que tem tantos containers, que a máquina começa a ficar lenta... Ou seja, está esgotando os recursos da máquina. 

    ![Untitled](Kubernetes%2056d70cbcc1484b3ab108dc6380a859e9/Untitled.png)

    Podemos resolver esse problema de 2 formais principais:

    1. Adicionar mais processamento, mais ram, aí sim o bichão dá conta de mais containers. (escalabilidade vertical)
    2. Adicionando mais máquinas pra suportar mais containers (escalabilidade horizontal)

    ![Untitled](Kubernetes%2056d70cbcc1484b3ab108dc6380a859e9/Untitled%201.png)

    É aí que o Kubernetes entra, na abordagem 2. Ele vai tomar de conta da comunicação entre essas máquinas, dividir o processamento entre elas, resumindo, ele vai gerenciar essas máquinas pra gente. Essa "rede" em que temos as máquinas trabalhando em conjunto, é chamada de **cluster.**

    Então, uma ou mais máquinas trabalhando em conjunto, dividindo o seu poder computacional, nós vamos chamar de um cluster. O Kubernetes é capaz de criar esse cluster e o gerenciar para nós.

    Então, digamos que nós queremos utilizar um container dentro de uma máquina do nosso cluster. Nós simplesmente pedimos ao Kubernetes que ponha esse container para executar dentro do nosso cluster e ele vai definir para ele aqui, usando os algoritmos que ele tem ali internamente - que, por exemplo, a nossa primeira máquina é a melhor a executar esse container.

    Caso esse container falhe, por exemplo, o Kubernetes tem total capacidade de adicionar um novo container para substituir esse que estava até então ali na nossa máquina e falhou.

    E caso essas máquinas atinjam o seu limite de poder computacional, o Kubernetes também é capaz de criar uma nova máquina dentro do nosso *cluster* para que nós consigamos também adicionar mais containers à essas máquinas, uma máquina virtual. 

    ![Untitled](Kubernetes%2056d70cbcc1484b3ab108dc6380a859e9/Untitled%202.png)

### Pods

- O que são Pods?

    Pods seria equivalente ao que é um Container no docker. 

    A diferença é que um Pod suporta um ou mais containers dentro dele. Seria algo como uma "network" lá do docker... mas é diferente. As networks do docker meio que conecta ali todo mundo em um só lugar, mas cada container tem um ip único. Já os Pods, cada Pod possui um IP e os containers dentro dele, vão ter o mesmo IP desse Pod. Ou seja, digamos que eu tenho um postgres em um container na porta 5432 e uma aplicação Spring na 8080, ambas no mesmo Pod. O Pod tem endereço ip de 127.0.18. Pra me comunicar com o postgres, eu vou no Ip do Pod e acesso a porta que quero:  127.0.18:5432 e vale pra Spring também: 127.0.18:8080. Ou seja, o mesmo IP. Sendo assim... posso usar localhost, pois estão no mesmo IP

- Criando um Pod

    ### Maneira imperativa:

    ```powershell
    kubectl run nginx-pod --image=nginx:latest
    ```

    Em que run é pra criar o pod, o `nginx-pod` é o nome desse pod e a imagem é em qual imagem ele é baseado.

    ### Maneira declarativa:

    Criamos um arquivo `.yml` e então começamos a declarar como queremos o Pod. Muito semelhante ao docker-compose.

    ![Untitled](Kubernetes%2056d70cbcc1484b3ab108dc6380a859e9/Untitled%203.png)

    Daí é só rodar:

    ```powershell
     kubectl apply -f .\primeiro-pod.yml
    ```

- Outros comandos Pods

    Quais comandos criam, descrevem e editam pods, respectivamente?
    `kubectl run, kubectl describe, kubectl edit`

- Deletando um Pod

    ```powershell
    Kubectl delete pod nome-do-pod
    ```

    ```powershell
    Kubectl delete -f .\primeiro-pod.yml
    ```

### Cluster IP

- O que são os Cluster IP?

    é um IP fixo configurado pra uso interno somente.

    No Kubernates, cada Pod tem um IP que muda, que não é estático. Ou seja, muito parecido com os containers do docker... e pra comunicar os bichos, como a gente faz no docker? Não criamos lá uma network e usamos os nomes do container na uri? tipo um jdbc://postgres:54321...? Dessa forma, não precisamos saber o IP desse postgres, já que é estático e sempre vai mudar... 

    Pois bem, o ClusterIP no Kubernetes, tem um papel semelhante (não o mesmo), eles vão fazer com que os Pod se comuniquem, já que o IP de um Pod não é estático. Então criamos um service que o IP interno dele não muda, bastando apenas interligar com o Pod que queremos nos conectar e também configurar a porta.

- Criando um Cluster IP

    ```yaml
    apiVersion: v1
    kind: Service
    metadata:
      name: svc-pod-1
    spec: 
      type: ClusterIP
      selector:
        app: pod-1
      ports:
        - port: 5000
          targetPort: 80
    ```

    Como um service sabe quais pods deve gerenciar?

    Através de labels definidas no metadata e utilizando o campo selector no service.

    No selector eu digo quem é o Pod que eu estou me conectando. E pra definir isso, eu preciso ir no meu `yml` da criação do Pod e adicionar essa chave e valor na tag de metadata. Também precisamos definir a porta dele

    ```yaml
    apiVersion: v1
    kind: Pod
    metadata:
      name: pod-1
      labels:
        app: pod-1
    spec: 
      containers:
        - name: nginx-container
          image: nginx:latest
          ports: 
            - containerPort: 80
    ```

### Node Port

- O que são os services NodePort?

    É um IP fixo pra uso externo e interno

    São eles que abrem a conexão pro mundo externo, ou seja, se no clusterIP os Pods se comunicam entre si por um IP, aqui fora também precisamos nos comunicar com esse cluster. É aí que entra o NodePort. 

    O NodePort também é um ClusterIP. Se tu quiser fazer um Pod se comunicar com um serviço externo, fora do cluster, teria que usar o NodePort. É como um portão da fronteira.. 

- Criando um Service Node Port

    Bem semelhante ao Cluster IP

    ```yaml
    apiVersion: v1
    kind: Service
    metadata:
      name: svc-pod-2
    spec: 
      type: NodePort
      selector:
        app: pod-2
      ports:
        - port: 80
          #targetPort vai a de cima por detault quando a gente deixa implicito
          nodePort: 30002 #porta externa
    ```

    Lembrando que precisamos configurar o Pod que vai ser bindado nele, assim como fizemos no

    [Criando um Cluster IP]()

### ConfigMap

- O que são os Config Map?

    São arquivos de configuração de variáveis de ambiente. Pra não sujar nosso arquivo yml com várias variáveis, deixando o arquivo de criação de Pod apenas pra ser um um arquivo de configuração. Os config map também permitem serem reutilizados em outros pods.

- Criando um Config Map

    ```yaml
    apiVersion: v1
    kind: ConfigMap
    metadata: 
      name: db-configmap
    data:
      MYSQL_ROOT_PASSWORD: q1w2e3r4
      MYSQL_DATABASE: empresa
      MYSQL_PASSWORD: q1w2e3r4
    ```

- Usando um Config Map em um Pod

    ```yaml
    apiVersion: v1
    kind: Pod
    metadata:
      name: sistema-noticias
      labels:
        app: sistema-noticias
    spec:
      containers:
        - name: sistema-noticias-container
          image: aluracursos/sistema-noticias:1
          ports:
            - containerPort: 80 
          envFrom: 
            - configMapRef:
                name: sistema-configmap
    ```

---

### Replica Sets

- O que são os Replica Sets

    Replica Set nada mais é do que esse conjunto de réplicas que permite a criação, de maneira automática, em caso de falhas de um Pod, dentro de um cluster gerenciado por um Replica Set.

    É uma estrutura que encapsula os Pods com o objetivo de fazê-los subir assim que forem derrubados.

    Naturalmente, quando os Pods são derrubados, morrem, erro, ou qualquer outra coisa que faça ele parar de funcionar, eles não voltam mais. 

    É pra isso que os Replica Sets encapsulam os containers, meio que pra "tomar de conta" dele, tá sobre a responsabilidade dele tomar as previdências necessárias pra fazê-lo permanecer vivo. Mesmo que um Pod nunca mais volte após morrer, ele vai e cria outro, pra manter aquela função funcionando de qualquer jeito.

    ![Untitled](Kubernetes%2056d70cbcc1484b3ab108dc6380a859e9/Untitled%204.png)

- Criando um replica set

    ```yaml
    apiVersion: apps/v1
    kind: ReplicaSet
    metadata:
      name: portal-noticias-replicaset
      labels:
        app: guestbook
        tier: frontend
    spec:
      replicas: 3
      selector:
        matchLabels:
          app: portal-noticias
      template:
        metadata:
          name: portal-noticias
          labels:
            app: portal-noticias
        spec:
          containers:
          - name: portal-noticias-container
            image: aluracursos/portal-noticias:1
            ports:
              - containerPort: 80 
            envFrom: 
              - configMapRef:
                  name: portal-noticias-configmap
    ```

### Deployment

- O que são os Deployment

    é uma camada que encapsula um replica set. Tipo um git.. vai controlar as versões das mudanças.

    Então, quando utilizamos nossos Deployments, o que conseguimos fazer? Conseguimos, simplesmente, ter uma camada extra acima de um ReplicaSet, que consegue gerenciar as imagens, todo o versionamento do que estamos definindo, controle de atualização em cima das nossas imagens e Pods.

    Quais das alternativas abaixo contém diferenças entre ReplicaSets e Deployments?

    Quando criados, Deployments auxiliam com controle de versionamento e criam um ReplicaSet automaticamente.

### Volumes

- O que são os volumes?

    Os Pods são efêmeros, preparados pra serem substituídos a qualquer momento. Sendo assim, o armazenamento dentro desse Pod, se perde cada vez que ele é derrubado. Por exemplo, um Pod com um PostgreSQL, tá lá rodando sem erros há 2 dias, com vários dados. De repente, ele cai e todos os dados dali são perdidos. Os dados não são persistidos de fato. 

    Pra resolver esse problema no docker, a gente usa os volumes, que é uma pasta que é criada por lá e a "cópia" dela no nosso computador. 

    O ciclo de vida dos volumes não é dependente de container, ou seja, se em um Pod existem 2 containers e 1 cair, o nosso volume vai manter os dados. Já se os 2 caírem, o Pod deixa de existir, sendo assim, adeus. Mas, o que acontece com os arquivos? Vai depender dos [tipo de volume](https://kubernetes.io/docs/concepts/storage/volumes/#volume-types). Em que o hostPath vai criar uma pasta lá no cluster do kubernates e essa mesma será "bindada" com uma pasta local. Nesse caso, os dados não se perdem. 

- Criando um volume

    ```yaml
    apiVersion: v1
    kind: Pod
    metadata:
      name: pod-volume
    spec:
      containers:
        - name: nginx-container
          image: nginx:latest
          volumeMounts:
            - mountPath: /volume-dentro-do-container
              name: primeiro-volume
        - name: jenkins-container
          image: jenkins:alpine
          volumeMounts:
            - mountPath: /volume-dentro-do-container
              name: primeiro-volume
      volumes:
        - name: primeiro-volume
          hostPath:
            path: /C/Users/Daniel/Desktop/primeiro-volume
            type: Directory
    ```