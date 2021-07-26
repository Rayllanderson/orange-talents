package com.rayllanderson.bank.modelo

interface Autenticavel {
    fun autentica(senha: Int) : Boolean
}