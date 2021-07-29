package com.rayllanderson.recursos.funcionais

fun main() {
    Pessoa().let { }
    "".let { }
    1.let { }

    val ray = Pessoa(nome = "Ray", peso = 10.0)
    val nomeEmMaiusculo = "${ray.nome?.toUpperCase()} pesando ${ray.peso}"
    println(nomeEmMaiusculo)

    //O LET, retorna o resultado da lambda.
    // aqui nesse caso, retorna a String, a ultima instrução, portanto, o println vai ser o retorno ali, a string.
    Pessoa(nome = "Ray", peso = 10.0).let { pessoa ->
        "${pessoa.nome?.toUpperCase()} pesando ${pessoa.peso}"
    }.let(::println)

    //o APPLY vai te retornar o próprio objeto, no nosso caso, pessoa.
    Pessoa(nome = "Ray", peso = 10.0).apply {
        nome = nome?.toUpperCase()
    }.let (::println)


    listaQueVemDoBanco().filter { pessoa -> pessoa.idade ?:0 > 18 }.let(::imprimirMaiores18)
    listaQueVemDoBanco().filter { pessoa -> pessoa.idade ?:99 < 18 }.let(::imprimirMenores18)



    autentica(123, autenticado = {
        println("Você está autenticado!")
    })
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

fun autentica(senha: Int, autenticado: () -> Unit){
    if(senha == 123) autenticado()
}
