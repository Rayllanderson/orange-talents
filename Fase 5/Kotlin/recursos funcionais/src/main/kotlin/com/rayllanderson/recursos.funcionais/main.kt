package com.rayllanderson.recursos.funcionais

fun main() {
    val minhaFuncao: (String) -> String = ::gretting
    val minhaFuncao2: () -> Unit = ::teste
    val minhaFuncaoClasse: () -> Unit = Teste()


    println(minhaFuncao("Ray"))
    minhaFuncao2()
    minhaFuncaoClasse()
}

fun gretting(nome: String): String = "Olá, $nome"
fun teste() = println("Rom")

class Teste: () -> Unit{
    override fun invoke() {
        println("Executando invoke")
    }

}