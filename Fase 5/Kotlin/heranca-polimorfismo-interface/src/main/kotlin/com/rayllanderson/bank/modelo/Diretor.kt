package com.rayllanderson.bank.modelo

class Diretor(
    nome: String,
    cpf: String,
    salario: Double,
    val plr: Int
) : Funcionario(
    nome = nome,
    cpf = cpf,
    salario = salario
) {

    override val bonificacao: Double
        get() {
            return this.salario * 0.2 + plr
        }

    override fun printarDados() {
        super.printarDados()
        println("Plr $plr")
    }
}