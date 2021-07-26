fun main() {
    val contaDoJoao = ContaPoupanca("João")

    val valorASerDepositado = 100.0
    val valorASerSacado = 50.0

    contaDoJoao.depositar(valorASerDepositado)
    contaDoJoao.sacar(valorASerSacado)

    contaDoJoao.printarDados()
}