package com.rayllanderson.colecoes

fun main() {
    val serie = 1.rangeTo(10) // de 1 a 10...
    serie.forEach{s -> print("$s ")}

    println()

    val numerosPares = 0..10 step 2 //de 1 a 10, pulando de 2 em 2...
    numerosPares.forEach{ s -> print("$s ")}

    println()

    val numerosParesReverso = 10 downTo 0 step 2
    numerosParesReverso.forEach{print("$it ")}

    println()

    val intervalos = 1000.0..5000.0
    val salario = 1200.0
    if (salario in intervalos) println("Salário está dentro do intervalo")

    val alfabeto = 'a'..'z'
    alfabeto.forEach{print("$it ")}

    println()

    val letra = 'r'
    println(letra in alfabeto)
}