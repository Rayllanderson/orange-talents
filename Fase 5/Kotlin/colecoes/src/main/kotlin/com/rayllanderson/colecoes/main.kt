package com.rayllanderson.colecoes

fun main() {
    /*
    val tamanhoDoArray = 4
    val idades = IntArray(tamanhoDoArray)
    idades[0] = 25
    idades[1] = 20
    idades[2] = 23
    idades[3] = 24
    */

    val idades: IntArray = intArrayOf(25, 25, 23, 24, 22)

    var maiorIdade = Int.MIN_VALUE
    var menorIdade = Int.MAX_VALUE
    idades.forEach { idade ->
        maiorIdade = if(idade > maiorIdade) idade else maiorIdade
        menorIdade = if (idade < menorIdade) idade else menorIdade
    }
//    val maiorIdade = idades.maxOrNull()
//    val menorIdade = idades.minOrNull()

    println("Maior idade $maiorIdade")
    println("Menor idade $menorIdade")
}