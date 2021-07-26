class SistemaInterno {
    fun entra(usuario: Autenticavel, senha: Int) {
        val estaAutenticado = usuario.autentica(senha = senha)
        if (estaAutenticado) println("Logado com sucesso")
        else println("Não autenticado. Senha inválida")
    }
}