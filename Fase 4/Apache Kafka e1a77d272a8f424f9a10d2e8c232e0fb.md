# Apache Kafka

Criando um tópico

```bash
docker exec 8ab0b9434e20 kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partit
ions 1 --topic LOJA_NOVO_PEDIDO
```

Listando os tópicos

```bash
docker exec 8ab0b9434e20 kafka-topics --list --bootstrap-server localhost:9092
```

Criando um produtor

```bash
docker exec -it 8ab0b9434e20 kafka-console-producer --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
```

Consumindo 

```bash
docker exec -it 8ab0b9434e20 kafka-console-consumer --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
```