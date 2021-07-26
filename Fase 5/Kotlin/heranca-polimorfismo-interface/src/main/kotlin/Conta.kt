class Conta(
    private val titular: String
) {
    private var saldo = 0.0
        private set(value) {
            if (value > 0) {
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