package com.rayllanderson.recursos.funcionais

fun main() {
    Pessoa().let { }
    "".let { }
    1.let { }

    val ray = Pessoa(nome = "Ray", peso = 10.0)
    val nomeEmMaiusculo = "${ray.nome?.toUpperCase()} pesando ${ray.peso}"
    println(nomeEmMaiusculo)

    Pessoa(nome = "Ray", peso = 10.0).let { pessoa ->
        "${pessoa.nome?.toUpperCase()} pesando ${pessoa.peso}"
    }.let(::println)


    listaQueVemDoBanco().filter { pessoa -> pessoa.idade ?:0 > 18 }.let(::imprimirMaiores18)
    listaQueVemDoBanco().filter { pessoa -> pessoa.idade ?:99 < 18 }.let(::imprimirMenores18)
}

fun listaQueVemDoBanco(): List<Pessoa> {
    return listOf(
        Pessoa(nome = "Kaguya", idade = 16),
        Pessoa(nome = "Hayasaka", idade = 16),
        Pessoa("João", idade = 22),
        Pessoa("Zoro", idade = 21),
        Pessoa("Lek nulo")
    )
}

fun imprimirMaiores18(pessoas: List<Pessoa>){
    templateImprimirPessoas("Pessoas maiores de 18 anos: ", pessoas)
}

fun imprimirMenores18(pessoas: List<Pessoa>){
    templateImprimirPessoas("Pessoas menores de 18 anos: ", pessoas)
}

private fun templateImprimirPessoas(mensagem: String, pessoas: List<Pessoa>){
    println(mensagem)
    println(pessoas)
    println("--------------------------")
}
