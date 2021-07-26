package com.rayllanderson.bank.modelo

import kotlin.random.Random

abstract class Conta(
    private val titular: String
) {
    private val numero = Random(1000).nextInt()

    private var saldo = 0.0

    fun depositar(valor: Double) {
        this.saldo += valor
    }

    open fun sacar(valor: Double) {
        if (temSaldo(valor)) this.saldo -= valor
        else println("Não é possível sacar. A conta não tem saldo suficiente")
    }

    fun printarSaldo() {
        println(this.saldo)
    }

    fun printarDados(){
        println("Titular $titular")
        println("Número da conta $numero")
        println("Saldo R$ $saldo")
    }

    fun transferir(contaDeDestino: Conta, valor: Double) {
        if (temSaldo(valor)) {
            contaDeDestino.depositar(valor)
            this.saldo -= valor
        } else println("Não é possível transferir. A conta não tem saldo suficiente")
    }

    private fun temSaldo(valor: Double): Boolean {
        return this.saldo >= valor
    }
}