package com.rayllanderson.bank.modelo

abstract class Funcionario(
    val nome: String,
    val cpf: String,
    val salario: Double
){

    abstract val bonificacao: Double

    open fun printarDados(){
        println("Nome $nome")
        println("Cpf $cpf")
        println("Salário R$ $salario")
        println("Bonificação R$ ${bonificacao}")
    }
}