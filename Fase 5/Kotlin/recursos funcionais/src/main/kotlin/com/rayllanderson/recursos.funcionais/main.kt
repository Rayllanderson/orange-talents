package com.rayllanderson.recursos.funcionais

fun main() {
    soma(1, 3) { resultado ->
        println(resultado)
    }

    somaReceiver(1, 3) {
        println(this)
    }
}

fun soma(a: Int, b: Int, resultado: (Int) -> Unit){
    resultado(a + b)
}
fun somaReceiver(a: Int, b: Int, resultado: Int.() -> Unit){
    resultado(a + b)
}

fun testaRun() {
    // RUN
    // explicando o code...
    // criei uma instância de pessoa, e ela foi passada pelo construtor
    // na conta poupança, que precisa de um cliente
    // o LET, retorna a lambda, portanto, retornou a conta...
    // Com a conta, eu fiz um RUN, que vai pegar o próprio objeto e retornar a LAMBDA
    Pessoa(nome = "Ray", idade = 23).let { clienteNovo ->
        ContaPoupanca(clienteNovo)
    }.run {
        this.saldo + 100
    }.let { saldoAtual ->
        println(saldoAtual)
    }
}

fun testaWith() {
    // WITH
    // recebe o objeto, lá dentro do escopo tratamos o objeto
    // retorna o resultado da lambda, no nosso caso, o método apresenta, que retorna uma string
    with(Pessoa()) {
        nome = "Ray"
        idade = 23
        peso = 1.0
        apresenta()
    }.let { apresentacao: String ->
        println(apresentacao)
    }
}

