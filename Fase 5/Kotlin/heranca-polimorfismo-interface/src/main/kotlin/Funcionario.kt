class Funcionario(
    val nome: String,
    val cpf: String,
    val salario: Double
){

    fun bonificacao(): Double{
        return this.salario * 0.1
    }

    fun printarDados(){
        println("Nome $nome")
        println("Cpf $cpf")
        println("Salário R$ $salario")
        println("Bonificação R$ ${bonificacao()}")
    }
}