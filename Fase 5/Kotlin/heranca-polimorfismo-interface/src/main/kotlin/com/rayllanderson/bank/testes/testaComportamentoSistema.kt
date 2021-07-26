import com.rayllanderson.bank.modelo.Cliente
import com.rayllanderson.bank.modelo.Gerente
import com.rayllanderson.bank.modelo.SistemaInterno

fun testaComportamentoSistema() {
    val hayasaka = Gerente(
        "Hayasaka",
        "054952",
        4000.0,
        123
    )
    val joao = Cliente("João", 1234)

    val sistemaInterno = SistemaInterno()
    sistemaInterno.entra(hayasaka, 123)
    sistemaInterno.entra(joao, 123)
}