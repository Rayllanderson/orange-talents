package com.rayllanderson.recursos.funcionais

data class Pessoa(
    var nome: String? = null,
    var peso: Double? = null,
    var idade: Int? = null
) {
    fun apresenta() : String{
        return """
            Olá, me chamo $nome e tenho $idade anos.
            Estou atualmente pesando $peso kg
        """.trimIndent()
    }
}