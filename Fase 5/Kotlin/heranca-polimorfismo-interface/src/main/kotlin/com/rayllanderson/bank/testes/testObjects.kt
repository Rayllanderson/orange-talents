package com.rayllanderson.bank.testes

import com.rayllanderson.bank.modelo.Conta

fun testaObjects(){
    testaComportamentoConta()
    println("Numero total de contas criadas no sistema: ${Conta.total}" )

    //Object declaration... bem parecido com os obj do JS
    val objectDeclarion = object {
        val nome = "nome"
        fun sayHi(){
            print(nome)
        }
    }

    objectDeclarion.sayHi()
}