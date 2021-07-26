package com.rayllanderson.bank.testes

import com.rayllanderson.bank.modelo.Cliente
import com.rayllanderson.bank.modelo.ContaPoupanca

fun testaComportamentoConta() {
    val contaDaMaria = ContaPoupanca(Cliente("Maria", 123))
    val contaDoJoao = ContaPoupanca(Cliente("Joao", 1223))

    contaDaMaria.printarSaldo()

    contaDaMaria.depositar(50.0)
    contaDaMaria.printarSaldo()

    contaDaMaria.sacar(500.0)
    contaDaMaria.sacar(40.1)
    contaDaMaria.printarSaldo()

    contaDaMaria.transferir(contaDoJoao, 10.0)
    contaDaMaria.printarSaldo()
    contaDoJoao.printarSaldo()
    contaDoJoao.printarDados()
}