package com.rayllanderson.bank.modelo

import kotlin.random.Random

abstract class Conta(
    val titular: Cliente
) {
    private val numero = Random(1000).nextInt()

    private var saldo = 0.0

    //companion object é um objeto global. Tipo estático... wtf kkk
    companion object {
        var total: Int = 0
            private set

        private fun contar(){
            this.total++
        }
    }

    init {
        contar()
    }

    fun depositar(valor: Double) {
        this.saldo += valor
    }

    open fun sacar(valor: Double) {
        if (temSaldo(valor)) this.saldo -= valor
        else throw IllegalStateException("Não é possível sacar. A conta não tem saldo suficiente")
    }

    fun printarSaldo() {
        println(this.saldo)
    }

    fun printarDados(){
        println("Titular ${titular.nome}")
        println("Número da conta $numero")
        println("Saldo R$ $saldo")
    }

    fun transferir(contaDeDestino: Conta, valor: Double) {
        if (temSaldo(valor)) {
            contaDeDestino.depositar(valor)
            this.saldo -= valor
        } else throw IllegalStateException("Não é possível transferir. A conta não tem saldo suficiente")
    }

    private fun temSaldo(valor: Double): Boolean {
        return this.saldo >= valor
    }
}