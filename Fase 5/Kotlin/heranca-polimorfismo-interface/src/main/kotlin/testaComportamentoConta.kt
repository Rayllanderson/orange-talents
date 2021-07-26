fun testaComportamentoConta() {
    val contaDaMaria = ContaPoupanca("Maria")
    val contaDoJoao = ContaPoupanca("Joao")

    contaDaMaria.printarSaldo()

    contaDaMaria.depositar(50.0)
    contaDaMaria.printarSaldo()

    contaDaMaria.sacar(500.0)
    contaDaMaria.sacar(40.1)
    contaDaMaria.printarSaldo()

    contaDaMaria.transferir(contaDoJoao, 10.0)
    contaDaMaria.printarSaldo()
    contaDoJoao.printarSaldo()
    contaDoJoao.printarDados()
}