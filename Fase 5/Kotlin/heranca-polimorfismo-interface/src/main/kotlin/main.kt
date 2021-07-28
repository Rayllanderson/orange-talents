import com.rayllanderson.bank.modelo.Cliente
import com.rayllanderson.bank.modelo.Conta

fun main() {

    // o ? indica que o resultado pode ser nulo
    val numero: Double? = try {"ççç".toDouble()} catch (e:NumberFormatException ){null}
    numero?.toBigDecimal() //sem o ? daria um null pointer exception

    //usando o LET
    val qualquerValor = 123.90
    val conta: Conta? = null

    //pra cada coisa, vou ter que usar o ?... por exemplo:
    println(conta?.titular?.nome)
    conta?.depositar(qualquerValor)
    conta?.sacar(qualquerValor)

    //usando o LET, não preciso ficar colocando o ? pra tudo.
    conta?.let {conta: Conta -> {
        println(conta.titular.nome)
        conta.depositar(qualquerValor)
        conta.sacar(qualquerValor)
    }}

    // Elvis operator
    // é tipo um "? :" do java. "int num = num1 != null ? 3 : 4"
    // só que aqui ele verifica com null safety
    val outroNumero: Double = conta?.titular?.nome?.length?.toDouble() ?: 3.4
    println(outroNumero)

    // também podemos lançar exception... tipo um or else throw
    val numerao: Int = conta?.titular?.nome?.length ?: throw IllegalArgumentException("João")
    println(numerao)

    println(numero)



    imprime(Cliente("Joao", 21))
}

fun imprime(objeto: Any) {
    println(objeto)
}