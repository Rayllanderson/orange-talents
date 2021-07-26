import com.rayllanderson.bank.modelo.Conta
import com.rayllanderson.bank.testes.testaComportamentoConta
import com.rayllanderson.bank.testes.testaComportamentoFuncionario

fun main() {
    testaComportamentoFuncionario()
    testaComportamentoConta()
    println("Numero total de contas criadas no sistema: ${Conta.total}" )

    //Object declaration... bem parecido com os obj do JS
    var objectDeclarion = object {
        val nome = "nome"
        fun sayHi(){
            print(nome)
        }
    }

    objectDeclarion.sayHi()
}