package com.rayllanderson.recursos.funcionais


 fun testFuncaoPorFuncao() {
    // sem parâmetros
    val minhaFuncao2: () -> Unit = ::teste
    minhaFuncao2()

    // com parâmetros
    val dizerOiPara: (String) -> String = ::dizOi
    println(dizerOiPara("Ray"))
}

 fun testFuncaoPorClasse() {
    val somador: (Int, Int) -> Int = Somador()
    print(somador(1, 3))
}

 fun testaFuncaoAnonima() {
    val minhaFuncaoAnonima: () -> Unit = fun() { println("Yo...") }
    println(minhaFuncaoAnonima())

    val soma: (Int, Int) -> Int = fun(a: Int, b: Int) = a + b
    println(soma(1, 3))
}

 fun testaFuncaoLambda() {
    //sem parâmetros
    val minhaFuncaoLambda: () -> Unit = { println("Yoo... 2") }
    println(minhaFuncaoLambda())

    //com parâmetros
    val somaLambda: (Int, Int) -> Int = { a, b -> a + b }
    val somaLambda2 = { a: Int, b: Int -> a + b }
    println(somaLambda(1, 3))

    val maisDeUmRetorno: (Double) -> Double = resultado@{
        if (it > 100) return@resultado it + 50
        it + 100
        // ou return@resultado a + 100
    }

    val acimaDeCem = maisDeUmRetorno(500.0) // 550
    val abaixoDeCem = maisDeUmRetorno(100.00) // 200
    println(acimaDeCem)
    println(abaixoDeCem)
}


fun dizOi(nome: String): String = "Olá, $nome"
fun teste() = println("Rom")

class Somador: (Int, Int) -> Int{
    override fun invoke(p1: Int, p2: Int) : Int = p1 + p2
}