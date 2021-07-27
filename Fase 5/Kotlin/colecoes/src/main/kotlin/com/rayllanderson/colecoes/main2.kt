package com.rayllanderson.colecoes


//   Usando for each, o valor é imutável, não posso passar outro valor pra ele.
//   Pra isso, preciso acessar o index no array e aí sim mudar...
//
//    for (salario in salarios) {
//        salario = salario * aumento
//    }
//    Não pode ser reatribuído...

fun main() {

    val salarios = doubleArrayOf(500.0, 2500.0, 4000.0, 4500.0)

    val aumento = 1.1
    for (i in salarios.indices) {
        salarios[i] = salarios[i] * aumento
    }

    println(salarios.contentToString())

    //usando for each
    salarios.forEachIndexed { i, salario ->
        salarios[i] = salario * aumento
    }

    println(salarios.contentToString())
}