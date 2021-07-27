package com.rayllanderson.colecoes

import java.math.BigDecimal

fun main() {
    val salarios = bigDecimalArrayOf("1500.2", "5500.54", "500.42", "8500.0", "400.0")
    val salariosComAumento = salarios.map { salario -> darAumento(salario) }.toTypedArray()

    println("Salários base ${salarios.contentToString()}")
    println("Salários com aumento ${salariosComAumento.contentToString()}")
    println("Soma de todos os salários este mês R$ ${salariosComAumento.soma()}")
    println("Soma de todos os salários nos próximos 6 meses R$ ${salariosComAumento.getGastoTotalApos(6)}")
}

fun Array<BigDecimal>.getGastoTotalApos(meses: Int): BigDecimal{
    return this.fold(0.toBigDecimal()){somador, salario ->
        somador + (salario * meses.toBigDecimal())
    }
}

fun bigDecimalArrayOf(vararg elementos: String): Array<BigDecimal> {
    return Array(size = elementos.size){ i -> elementos[i].toBigDecimal()}
}

fun darAumento(salario: BigDecimal): BigDecimal{
    val aumento = "1.1".toBigDecimal()
    return if (salario < "5000.0".toBigDecimal()) salario + "500.0".toBigDecimal() else salario * aumento
}

fun Array<BigDecimal>.soma(): BigDecimal{
    return this.reduce{acumulador, valor ->
        acumulador + valor
    }
}