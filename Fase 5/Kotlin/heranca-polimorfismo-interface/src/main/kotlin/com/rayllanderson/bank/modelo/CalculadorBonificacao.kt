package com.rayllanderson.bank.modelo

class CalculadorBonificacao {
    var total: Double = 0.0
        private set

    private fun registra(funcionario: Funcionario){
        this.total += funcionario.bonificacao
    }

    fun registra(funcionario: Funcionario, vararg funcionarios: Funcionario){
        this.registra(funcionario)
        funcionarios.forEach { funcionario1 ->  this.registra(funcionario1)}
    }

    fun printarTotal(){
        println("Total $total")
    }
}