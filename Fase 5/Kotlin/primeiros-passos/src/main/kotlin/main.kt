fun main(){
    //Val é tipo final, e var é uma variavel que modifica. igual let e const no js
    println("Hello world")
    val titular = "Ray"
    val numeroConta = 12411
    var saldo = 0.0

    println("Titular = $titular")
    println("Número da conta = $numeroConta")
    println("Saldo = $saldo")

    when{
        saldo > 0.0 -> println("saldo positivo")
        saldo == 0.0 -> println("conta sem saldo")
        else -> println("conta com saldo negativo")
    }


    for (i in 1..5){
        println(i)
    }

    println()

    for (i in 1..3 step 2){
        println(i)
    }

    println()

    for(i in 5 downTo 1){
        println(i)
        when (i) {
            3 -> break
        }
    }

    loop@ for (i in 1..100) {
        println("i $i")
        for (j in 1..100) {
            println("j $j")
            if (j == 5) break@loop
        }
    }
}