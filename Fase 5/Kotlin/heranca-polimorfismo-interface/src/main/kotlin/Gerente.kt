class Gerente(
    nome: String,
    cpf: String,
    salario: Double,
    val senha: Int
) : Funcionario(
    nome = nome,
    cpf = cpf,
    salario = salario
), Autenticavel {

    override val bonificacao: Double
        get() {
            return salario * 0.3
        }

    override fun autentica(senha: Int): Boolean {
        return this.senha == senha
    }
}