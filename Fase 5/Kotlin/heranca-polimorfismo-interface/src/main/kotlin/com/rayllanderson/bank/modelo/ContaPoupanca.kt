package com.rayllanderson.bank.modelo

class ContaPoupanca(
    titular: Cliente
) : Conta(
    titular = titular
) {
    override fun sacar(valor: Double) {
        val valorComTaxa = valor + 0.5
        super.sacar(valorComTaxa)
    }
}