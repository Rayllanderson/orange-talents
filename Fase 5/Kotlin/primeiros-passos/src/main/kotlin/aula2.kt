fun main() {
    val contaDaMaria = Conta("Maria")
    val contaDoJoao = Conta("Joao")

    contaDaMaria.printarSaldo()

    contaDaMaria.depositar(50.0)
    contaDaMaria.printarSaldo()

//    contaDaMaria.sacar(500.0)
//    contaDaMaria.sacar(40.1)
//    contaDaMaria.printarSaldo()

    contaDaMaria.transferir(contaDoJoao, 10.0)
    contaDaMaria.printarSaldo()
    contaDoJoao.printarSaldo()
}

class Conta(private var titular: String) {
    var saldo = 0.0
        private set(value){
            if (value > 0){
                field += value
            }
        }

    fun depositar(valor: Double) {
        this.saldo += valor
    }

    fun sacar(valor: Double) {
        if (temSaldo(valor)) this.saldo -= valor
        else println("Não é possível sacar. A conta não tem saldo suficiente")
    }

    fun printarSaldo() {
        println(this.saldo)
    }

    fun transferir(contaDeDestino: Conta, valor: Double) {
        if (temSaldo(valor)) {
            contaDeDestino.depositar(valor)
            this.saldo -= valor
        } else println("Não é possível transferir. A conta não tem saldo suficiente")
    }

    private fun temSaldo(valor: Double): Boolean {
        return this.saldo >= valor
    }
}