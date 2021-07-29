package com.rayllanderson.recursos.funcionais

data class ContaPoupanca(val titular: Pessoa) {
    var saldo: Double = 0.0

    fun deposita(valor: Double){
        this.saldo += valor
    }
}
