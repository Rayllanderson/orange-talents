fun main() {
    val kaguya = Diretor(
        nome = "Kaguya sama",
        cpf = "4666662",
        salario = 50000.0,
        senha = 123,
        plr = 5000
    )
    val hayasaka = Gerente(
        "Hayasaka",
        "054952",
        4000.0,
        123
    )

    kaguya.printarDados()
    println("---------------")
    hayasaka.printarDados()

    val calculadora = CalculadorBonificacao()
    calculadora.registra(kaguya, hayasaka)
    calculadora.printarTotal()
}