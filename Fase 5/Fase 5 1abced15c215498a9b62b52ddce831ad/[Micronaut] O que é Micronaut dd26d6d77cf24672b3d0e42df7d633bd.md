# [Micronaut] O que é Micronaut?

Por que o micronaut e não o spring? Quais as diferenças?

Bom, ao subir um projeto com Spring, ele precisa levantar todo o contexto, carregar todos os beans, fazer as paradas de reflections lá e pa. Com isso, o uso de memória é um pouco mais elevado, por conta de todo esse processamento e gerenciamento.

Já o Micronaut, faz isso em tempo de compilação, por isso, acaba evitando todo o processamento de reflections.

Ou seja, o Micronaut vem pra resolver esses 2 problemas: O inicio rápido e o baixo consumo de memória. Sendo assim, fica mais muito mais rápido pra compilar e, pelo baixo consumo de memória, acaba ficando mais barato.

O Micronaut utiliza "Compilação AOT (Ahead-of-time compilation)"