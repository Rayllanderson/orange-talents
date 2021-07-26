class Diretor(
    nome: String,
    cpf: String,
    salario: Double,
    val senha: Int,
    val plr: Int
) : Funcionario(
    nome = nome,
    cpf = cpf,
    salario = salario
) {

    override val bonificacao: Double
        get() {
            return this.salario * 0.2 + plr
        }

    fun autentica(senha: Int): Boolean {
        return this.senha == senha
    }

    override fun printarDados() {
        super.printarDados()
        println("Plr $plr")
    }
}