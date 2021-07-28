package com.rayllanderson.colecoes

import java.math.BigDecimal

fun Array<BigDecimal>.getGastoTotalApos(meses: Int): BigDecimal {
    return this.fold(0.toBigDecimal()){somador, salario ->
        somador + (salario * meses.toBigDecimal())
    }
}

fun Array<BigDecimal>.soma(): BigDecimal{
    return this.reduce{acumulador, valor ->
        acumulador + valor
    }
}

fun Array<BigDecimal>.media(): BigDecimal{
    return this.soma() / this.size.toBigDecimal()
}
