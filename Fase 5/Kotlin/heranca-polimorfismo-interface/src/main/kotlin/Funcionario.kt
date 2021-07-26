open class Funcionario(
    val nome: String,
    val cpf: String,
    val salario: Double
){

    open val bonificacao: Double get() = this.salario * 0.1


    open fun printarDados(){
        println("Nome $nome")
        println("Cpf $cpf")
        println("Salário R$ $salario")
        println("Bonificação R$ ${bonificacao}")
    }
}